package pong.ios.boardcrud.adapter.out.persistence.post.mapper;

import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.board.entity.BoardEntity;
import pong.ios.boardcrud.adapter.out.persistence.post.entity.PostEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.mapper.UserMapper;
import pong.ios.boardcrud.domain.board.Board;
import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.global.mapper.Mapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper implements Mapper<Post, PostEntity> {
    private final UserMapper userMapper;

    public PostMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public PostEntity toEntity(Post domain) {
        UserEntity pinnedBy = domain.getPinnedBy() == null ? null : toUserRef(domain.getPinnedBy().getId());
        return PostEntity.builder()
                .id(domain.getId())
                .user(toUserRef(domain.getUser().getId()))
                .board(toBoardRef(domain.getBoard().getId()))
                .title(domain.getTitle())
                .content(domain.getContent())
                .category(domain.getCategory())
                .tags(new ArrayList<>(safeTags(domain.getTags())))
                .status(domain.getStatus())
                .isPublic(domain.isPublic())
                .isCommentAllowed(domain.isCommentAllowed())
                .isBlinded(domain.isBlinded())
                .isPinned(domain.isPinned())
                .pinnedAt(domain.getPinnedAt())
                .pinnedBy(pinnedBy)
                .viewCount(domain.getViewCount())
                .likeCount(domain.getLikeCount())
                .commentCount(domain.getCommentCount())
                .deletedAt(domain.getDeletedAt())
                .build();
    }

    public PostEntity toEntity(Post domain, UserEntity userEntity, BoardEntity boardEntity, UserEntity pinnedByEntity) {
        return PostEntity.builder()
                .id(domain.getId())
                .user(userEntity)
                .board(boardEntity)
                .title(domain.getTitle())
                .content(domain.getContent())
                .category(domain.getCategory())
                .tags(new ArrayList<>(safeTags(domain.getTags())))
                .status(domain.getStatus())
                .isPublic(domain.isPublic())
                .isCommentAllowed(domain.isCommentAllowed())
                .isBlinded(domain.isBlinded())
                .isPinned(domain.isPinned())
                .pinnedAt(domain.getPinnedAt())
                .pinnedBy(pinnedByEntity)
                .viewCount(domain.getViewCount())
                .likeCount(domain.getLikeCount())
                .commentCount(domain.getCommentCount())
                .deletedAt(domain.getDeletedAt())
                .build();
    }

    @Override
    public Post toDomain(PostEntity entity) {
        User pinnedBy = entity.getPinnedBy() == null ? null : User.builder().id(entity.getPinnedBy().getId()).build();
        return Post.builder()
                .id(entity.getId())
                .user(userMapper.toDomain(entity.getUser()))
                .board(Board.builder().id(entity.getBoard().getId()).build())
                .title(entity.getTitle())
                .content(entity.getContent())
                .category(entity.getCategory())
                .tags(List.copyOf(safeTags(entity.getTags())))
                .status(entity.getStatus())
                .isPublic(entity.isPublic())
                .isCommentAllowed(entity.isCommentAllowed())
                .isBlinded(entity.isBlinded())
                .isPinned(entity.isPinned())
                .pinnedAt(entity.getPinnedAt())
                .pinnedBy(pinnedBy)
                .viewCount(entity.getViewCount())
                .likeCount(entity.getLikeCount())
                .commentCount(entity.getCommentCount())
                .deletedAt(entity.getDeletedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private UserEntity toUserRef(Long id) {
        return UserEntity.builder().id(id).build();
    }

    private BoardEntity toBoardRef(Long id) {
        return BoardEntity.builder().id(id).build();
    }

    private List<String> safeTags(List<String> tags) {
        return tags == null ? List.of() : tags;
    }
}
