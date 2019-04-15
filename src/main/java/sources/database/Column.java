package sources.database;

class Column {
    private final String name, type;

    Column(String name, String type) {
        this.name = name;
        this.type = type;
    }

    String getName() {
        return name;
    }

    String getType() {
        return type;
    }

}
