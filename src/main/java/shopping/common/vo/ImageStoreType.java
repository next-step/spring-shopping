package shopping.common.vo;

public enum ImageStoreType {
    //todo: local static dns 적용
    LOCAL_STATIC("http://localhost:8080/image/"),
    NONE(""),
    ;

    private final String path;

    ImageStoreType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
