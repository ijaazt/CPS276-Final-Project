package sources.database;

class UserContract {
    static final String TABLE_NAME = "User";
    static final Column TABLE_COLUMN_ID = new Column("id", "INT PRIMARY KEY AUTO_INCREMENT");
    static final Column TABLE_COLUMN_USER_NAME = new Column("username", "VARCHAR(60)");
    static final Column TABLE_COLUMN_FIRST_NAME = new Column("firstName", "VARCHAR(60)");
    static final Column TABLE_COLUMN_LAST_NAME = new Column("lastName", "VARCHAR(60)");
    static final Column TABLE_COLUMN_PASSWORD = new Column("password", "VARCHAR(60)");
}
