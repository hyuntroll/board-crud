package pong.ios.boardcrud.domain.entity.user;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name="user")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column()
    private String role;

    @Column(name="u_email", nullable = false, unique = true)
    private String email;


}
