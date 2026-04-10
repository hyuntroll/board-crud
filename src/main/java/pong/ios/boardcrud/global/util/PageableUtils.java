package pong.ios.boardcrud.global.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import pong.ios.boardcrud.global.data.PageQuery;

import java.util.Set;

public final class PageableUtils {
    private PageableUtils() {
    }

    public static Pageable toPageable(PageQuery query, Set<String> sortableFields, String defaultSortField) {
        String sortBy = sortableFields.contains(query.sortBy()) ? query.sortBy() : defaultSortField;
        Sort.Direction direction = "asc".equalsIgnoreCase(query.direction()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        return PageRequest.of(query.page(), query.size(), Sort.by(direction, sortBy));
    }
}
