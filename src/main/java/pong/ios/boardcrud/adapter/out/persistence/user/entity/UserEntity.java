package pong.ios.boardcrud.adapter.out.persistence.user.entity;

import jakarta.persistence.*;
import lombok.*;
import pong.ios.boardcrud.domain.user.UserRoleType;
import pong.ios.boardcrud.global.entity.BaseEntity;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "tb_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoleType role;

    @Column
    private LocalDate birthDate;

    @Column
    private String profile;

}
