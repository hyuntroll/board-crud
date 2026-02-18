package pong.ios.boardcrud.domain.board;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Board {
    private Long id;
    private String name;
    private String type;
    private String description;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void updateInfo(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public void deactivate() {
        this.isActive = false;
    }
}
