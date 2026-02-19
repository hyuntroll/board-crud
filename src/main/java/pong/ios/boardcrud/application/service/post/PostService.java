package pong.ios.boardcrud.application.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pong.ios.boardcrud.application.port.in.post.CreatePostUseCase;
import pong.ios.boardcrud.application.port.in.post.DeletePostUseCase;
import pong.ios.boardcrud.application.port.in.post.GetPostUseCase;
import pong.ios.boardcrud.application.port.in.post.UpdatePostUseCase;
import pong.ios.boardcrud.application.port.in.post.dto.CreatePostCommand;
import pong.ios.boardcrud.application.port.in.post.dto.PostResult;
import pong.ios.boardcrud.application.port.in.post.dto.UpdatePostCommand;
import pong.ios.boardcrud.application.port.out.board.LoadBoardManagerPort;
import pong.ios.boardcrud.application.port.out.board.LoadBoardPort;
import pong.ios.boardcrud.application.port.out.post.LoadPostPort;
import pong.ios.boardcrud.application.port.out.post.SavePostPort;
import pong.ios.boardcrud.application.port.out.user.LoadUserPort;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.board.BoardStatusCode;
import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.post.PostErrorStatusCode;
import pong.ios.boardcrud.domain.post.PostStatus;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.domain.user.UserRoleType;
import pong.ios.boardcrud.domain.user.UserStatusCode;
import pong.ios.boardcrud.global.exception.ApplicationException;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;
import pong.ios.boardcrud.global.infra.security.holder.SecurityHolder;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService implements CreatePostUseCase, UpdatePostUseCase, DeletePostUseCase, GetPostUseCase {
    private final LoadPostPort loadPostPort;
    private final SavePostPort savePostPort;
    private final LoadUserPort loadUserPort;
    private final LoadBoardManagerPort loadBoardManagerPort;
    private final LoadBoardPort loadBoardPort;
    private final SecurityHolder securityHolder;

    @Override
    @Transactional
    public PostResult createPost(CreatePostCommand command) {
        User writer = getCurrentUser();
        Board board = getActiveBoard(command.boardId());
        LocalDateTime now = LocalDateTime.now();

        Post saved = savePostPort.save(
                Post.builder()
                        .user(writer)
                        .board(board)
                        .title(command.title())
                        .content(command.content())
                        .category(command.category())
                        .tags(normalizeTags(command.tags()))
                        .status(PostStatus.PUBLISHED)
                        .isPinned(false)
                        .viewCount(0)
                        .likeCount(0)
                        .commentCount(0)
                        .createdAt(now)
                        .updatedAt(now)
                        .build()
        );

        return PostResult.from(saved);
    }

    @Override
    @Transactional
    public PostResult updatePost(UpdatePostCommand command) {
        User requester = getCurrentUser();
        Post post = getPost(command.postId());
        validateNotDeleted(post);
        validateWriter(post, requester.getId());
        post.updateEditableFields(
                command.title(),
                command.content(),
                command.category(),
                normalizeTags(command.tags()),
                LocalDateTime.now()
        );
        Post updated = savePostPort.save(post);

        return PostResult.from(updated);
    }

    @Override
    @Transactional
    public void deletePost(Long postId) {
        User requester = getCurrentUser();
        Post post = getPost(postId);
        validateNotDeleted(post);
        validateDeletable(post, requester);
        LocalDateTime now = LocalDateTime.now();
        post.softDelete(now, now);
        savePostPort.save(post);
    }

    @Override
    public PageResult<PostResult> getPosts(PageQuery query) {
        return loadPostPort.findAll(query).map(PostResult::from);
    }

    @Override
    public PageResult<PostResult> getPostsByBoard(Long boardId, PageQuery query) {
        getActiveBoard(boardId);
        return loadPostPort.findAllByBoardId(boardId, query).map(PostResult::from);
    }

    @Override
    public PostResult getPostDetail(Long postId) {
        Post post = getPost(postId);
        validateNotDeleted(post);
        return PostResult.from(post);
    }

    private Post getPost(Long postId) {
        return loadPostPort.findById(postId)
                .orElseThrow(() -> new ApplicationException(PostErrorStatusCode.POST_NOT_FOUND));
    }

    private User getCurrentUser() {
        Long currentUserId = securityHolder.getCurrentUserId();
        return loadUserPort.findById(currentUserId)
                .orElseThrow(() -> new ApplicationException(UserStatusCode.USER_NOT_FOUND));
    }

    private Board getActiveBoard(Long boardId) {
        Board board = loadBoardPort.findById(boardId)
                .orElseThrow(() -> new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND));
        if (!board.isActive()) {
            throw new ApplicationException(BoardStatusCode.BOARD_NOT_FOUND);
        }
        return board;
    }

    private void validateWriter(Post post, Long requesterId) {
        if (!post.getUser().getId().equals(requesterId)) {
            throw new ApplicationException(PostErrorStatusCode.POST_FORBIDDEN);
        }
    }

    private void validateDeletable(Post post, User requester) {
        boolean isWriter = post.getUser().getId().equals(requester.getId());
        boolean isAdmin = requester.getRole() == UserRoleType.ADMIN;
        boolean isBoardManager = loadBoardManagerPort.existsById(post.getBoard().getId(), requester.getId());
        if (!isWriter && !isAdmin && !isBoardManager) {
            throw new ApplicationException(PostErrorStatusCode.POST_FORBIDDEN);
        }
    }

    private void validateNotDeleted(Post post) {
        if (post.getStatus() == PostStatus.DELETED) {
            throw new ApplicationException(PostErrorStatusCode.POST_DELETED);
        }
    }

    private List<String> normalizeTags(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return List.of();
        }
        return tags.stream()
                .map(String::trim)
                .filter(tag -> !tag.isBlank())
                .distinct()
                .toList();
    }
}
