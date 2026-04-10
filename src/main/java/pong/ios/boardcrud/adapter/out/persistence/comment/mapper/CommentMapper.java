package pong.ios.boardcrud.adapter.out.persistence.comment.mapper;

import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.comment.entity.CommentEntity;
import pong.ios.boardcrud.adapter.out.persistence.post.entity.PostEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.mapper.UserMapper;
import pong.ios.boardcrud.domain.comment.Comment;
import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.global.mapper.Mapper;

@Component
public class CommentMapper implements Mapper<Comment, CommentEntity> {
    private final UserMapper userMapper;

    public CommentMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public CommentEntity toEntity(Comment domain) {
        return CommentEntity.builder()
                .id(domain.getId())
                .post(PostEntity.builder().id(domain.getPost().getId()).build())
                .user(UserEntity.builder().id(domain.getUser().getId()).build())
                .parent(domain.getParent() == null ? null : CommentEntity.builder().id(domain.getParent().getId()).build())
                .content(domain.getContent())
                .status(domain.getStatus())
                .likeCount(domain.getLikeCount())
                .deletedAt(domain.getDeletedAt())
                .build();
    }

    @Override
    public Comment toDomain(CommentEntity entity) {
        return Comment.builder()
                .id(entity.getId())
                .post(Post.builder().id(entity.getPost().getId()).build())
                .user(userMapper.toDomain(entity.getUser()))
                .parent(entity.getParent() == null ? null : Comment.builder().id(entity.getParent().getId()).build())
                .content(entity.getContent())
                .status(entity.getStatus())
                .likeCount(entity.getLikeCount())
                .deletedAt(entity.getDeletedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
