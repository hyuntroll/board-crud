package pong.ios.boardcrud.adapter.out.persistence.report.mapper;

import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.post.entity.PostEntity;
import pong.ios.boardcrud.adapter.out.persistence.report.entity.PostReportEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.adapter.out.persistence.user.mapper.UserMapper;
import pong.ios.boardcrud.domain.post.Post;
import pong.ios.boardcrud.domain.report.PostReport;
import pong.ios.boardcrud.global.mapper.Mapper;

@Component
public class PostReportMapper implements Mapper<PostReport, PostReportEntity> {
    private final UserMapper userMapper;

    public PostReportMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public PostReportEntity toEntity(PostReport domain) {
        return PostReportEntity.builder()
                .id(domain.getId())
                .post(PostEntity.builder().id(domain.getPost().getId()).build())
                .reporter(UserEntity.builder().id(domain.getReporter().getId()).build())
                .reason(domain.getReason())
                .description(domain.getDescription())
                .status(domain.getStatus())
                .build();
    }

    @Override
    public PostReport toDomain(PostReportEntity entity) {
        return PostReport.builder()
                .id(entity.getId())
                .post(Post.builder().id(entity.getPost().getId()).build())
                .reporter(userMapper.toDomain(entity.getReporter()))
                .reason(entity.getReason())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
