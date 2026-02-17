package pong.ios.boardcrud.adapter.out.persistence.user.mapper;

import org.springframework.stereotype.Component;
import pong.ios.boardcrud.adapter.out.persistence.user.entity.UserEntity;
import pong.ios.boardcrud.domain.user.User;
import pong.ios.boardcrud.global.mapper.Mapper;

@Component
public class UserMapper implements Mapper<User, UserEntity> {


    @Override
    public UserEntity toEntity(User domain) {
        return UserEntity.builder()
                .username(domain.getUsername())
                .nickname(domain.getNickname())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .role(domain.getRole())
                .birthDate(domain.getBirthDate())
                .profile(domain.getProfile())
                .build();
    }

    @Override
    public User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .role(entity.getRole())
                .birthDate(entity.getBirthDate())
                .profile(entity.getProfile())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
