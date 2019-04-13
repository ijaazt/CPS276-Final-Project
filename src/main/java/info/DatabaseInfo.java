package info;

public enum DatabaseInfo {
    URL("jdbc:h2:~/test"),
    USERNAME(""),
    PASSWORD(""),
    DRIVER_CLASS_NAME("org.h2.Driver");

    public String info() {
        return info;
    }
    private String info;

    DatabaseInfo(String info) {
        this.info = info;
    }
}
