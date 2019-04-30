package info;

public enum DatabaseInfo {
    URL("jdbc:mysql://wccnet.russet.edu:3306/mitello"),
    USERNAME("mitello"),
    PASSWORD("gVjdeH9DhVRw"),
    DRIVER_CLASS_NAME("com.mysql.cj.jdbc.Driver");

    public String info() {
        return info;
    }
    private String info;

    DatabaseInfo(String info) {
        this.info = info;
    }
}
