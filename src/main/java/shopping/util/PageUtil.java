package shopping.util;

public final class PageUtil {

    private static final int PAGE_START_NUMBER = 1;
    private static final int MIN_PAGE_SIZE = 6;
    private static final int MAX_PAGE_SIZE = 30;

    private PageUtil() {
    }

    public static int validatePageNumber(Integer pageNumber) {
        return (pageNumber < PAGE_START_NUMBER
                ? PAGE_START_NUMBER : pageNumber) - PAGE_START_NUMBER;
    }

    public static int validatePageSize(Integer pageSize) {
        if (pageSize > MAX_PAGE_SIZE) {
            return MAX_PAGE_SIZE;
        }
        if (pageSize < MIN_PAGE_SIZE) {
            return MIN_PAGE_SIZE;
        }
        return pageSize;
    }
}
