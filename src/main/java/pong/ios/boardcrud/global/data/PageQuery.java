package pong.ios.boardcrud.global.data;

import org.springframework.util.StringUtils;

public record PageQuery(
        int page,
        int size,
        String sortBy,
        String direction
) {
    public PageQuery {
        page = Math.max(page, 0);
        size = Math.min(Math.max(size, 1), 100);
        sortBy = !StringUtils.hasText(sortBy) ? "createdAt" : sortBy;
        direction = !StringUtils.hasText(direction) ? "desc" : direction;
    }
}
