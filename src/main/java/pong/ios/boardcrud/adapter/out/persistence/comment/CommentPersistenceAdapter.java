package pong.ios.boardcrud.adapter.out.persistence.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.comment.mapper.CommentMapper;
import pong.ios.boardcrud.adapter.out.persistence.comment.repository.CommentRepository;
import pong.ios.boardcrud.application.port.out.comment.LoadCommentPort;
import pong.ios.boardcrud.application.port.out.comment.SaveCommentPort;
import pong.ios.boardcrud.domain.comment.Comment;
import pong.ios.boardcrud.global.data.PageQuery;
import pong.ios.boardcrud.global.data.PageResult;
import pong.ios.boardcrud.global.util.PageableUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CommentPersistenceAdapter implements LoadCommentPort, SaveCommentPort {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public Optional<Comment> findById(Long commentId) {
        return commentRepository.findById(commentId)
                .map(commentMapper::toDomain);
    }

    @Override
    public PageResult<Comment> findAllByPostId(Long postId, PageQuery query) {
        Set<String> sortableFields = Set.of("createdAt", "updatedAt", "likeCount");
        Pageable pageable = PageableUtils.toPageable(query, sortableFields, "createdAt");

        Page<Comment> page = commentRepository.findAllByPost_Id(postId, pageable)
                .map(commentMapper::toDomain);

        return PageResult.from(page);
    }

    @Override
    public List<Comment> findAllByPostId(Long postId) {
        return commentRepository.findAllByPost_Id(postId).stream()
                .map(commentMapper::toDomain)
                .toList();
    }

    @Override
    public Comment save(Comment comment) {
        return commentMapper.toDomain(
                commentRepository.save(commentMapper.toEntity(comment))
        );
    }

    @Override
    public void saveAll(List<Comment> comments) {
        commentRepository.saveAll(
                comments.stream().map(commentMapper::toEntity).toList()
        );
    }
}
