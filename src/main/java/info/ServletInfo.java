package info;

public enum ServletInfo {
    JSP_ADDRESS("/PersonCollection.jsp"),
    CONNECTION_POOL("connectionPool"),
    PERSON_LIST("personList");
    private String info;

    public String info() {
        return info;
    }
    ServletInfo(String info) {
        this.info = info;
    }
}
