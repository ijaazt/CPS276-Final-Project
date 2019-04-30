package info;

public enum ServletInfo {
    REPOSITORY("repository"),
    USER_AUTH("userAuth");
    private String info;

    public String info() {
        return info;
    }
    ServletInfo(String info) {
        this.info = info;
    }
}
