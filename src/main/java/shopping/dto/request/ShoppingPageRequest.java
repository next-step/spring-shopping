package shopping.dto.request;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class ShoppingPageRequest extends PageRequest {

    private static final String SORTING_COLUMN = "id";
    private static final int PAGE_START_NUMBER = 1;
    private static final int MIN_PAGE_SIZE = 6;
    private static final int MAX_PAGE_SIZE = 30;

    protected ShoppingPageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
    }

    public static ShoppingPageRequest of(int page, int size) {
        int pageNumber = validatePageNumber(page);
        int pageSize = validatePageSize(size);
        return new ShoppingPageRequest(pageNumber, pageSize, Sort.unsorted());
    }

    public static ShoppingPageRequest recentPageOf(int page, int size) {
        int pageNumber = validatePageNumber(page);
        int pageSize = validatePageSize(size);
        return new ShoppingPageRequest(pageNumber, pageSize,
                Sort.by(Direction.DESC, SORTING_COLUMN));
    }

    private static int validatePageNumber(Integer pageNumber) {
        return (pageNumber < PAGE_START_NUMBER
                ? PAGE_START_NUMBER : pageNumber) - PAGE_START_NUMBER;
    }

    private static int validatePageSize(Integer pageSize) {
        if (pageSize > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE;
        }
        if (pageSize < MIN_PAGE_SIZE) {
            return MIN_PAGE_SIZE;
        }
        return pageSize;
    }
}

