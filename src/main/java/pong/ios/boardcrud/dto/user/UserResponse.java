package pong.ios.boardcrud.dto.user;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pong.ios.boardcrud.domain.entity.user.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private String username;

    private String email;

    private List<String> followers;

    private List<String> followings;

    public UserResponse(UserEntity user) {

        this.username = user.getUsername();

        this.email = user.getEmail();

        followers = user.getFollowers().stream()
                .map(UserEntity::getUsername)
                .collect(Collectors.toList());

        followings = user.getFollowing().stream()
                .map(UserEntity::getUsername)
                .collect(Collectors.toList());

    }

}
