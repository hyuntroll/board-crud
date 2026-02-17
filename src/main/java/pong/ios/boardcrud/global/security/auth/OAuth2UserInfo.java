package pong.ios.boardcrud.global.security.auth;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class OAuth2UserInfo {

    private String name;
    private String email;
    private String picture;
    private Map<String, Object> attributes;

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "google" -> googleOf(attributes);
            case "naver" -> naverOf(attributes);
            default -> throw new IllegalArgumentException("Invalid registration id");
        };
    }

    private static OAuth2UserInfo googleOf(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name(attributes.get("name").toString())
                .email(attributes.get("email").toString())
                .picture(attributes.get("picture").toString())
                .build();
    }

    private static OAuth2UserInfo naverOf(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name(attributes.get("name").toString())
                .email(attributes.get("email").toString())
                .attributes(attributes)
                .build();
    }

}
