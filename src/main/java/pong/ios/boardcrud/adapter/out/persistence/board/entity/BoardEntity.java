package pong.ios.boardcrud.adapter.out.persistence.board.entity;

import jakarta.persistence.*;
import lombok.*;
import pong.ios.boardcrud.global.entity.BaseEntity;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "tb_boards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private boolean isActive;

    public void update(String name, String type, String description, boolean isActive) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.isActive = isActive;
    }
}
