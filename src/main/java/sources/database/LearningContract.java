package sources.database;

class LearningContract {
    static final String TABLE_NAME = "LearningCollection";
    static final Column TABLE_COLUMN_ID = new Column("id", "INT PRIMARY KEY AUTO_INCREMENT");
    static final Column TABLE_COLUMN_LEARNING = new Column("learning", "VARCHAR(300)");
    static final Column TABLE_COLUMN_CATEGORY = new Column("category", "VARCHAR(400)");
    static final Column TABLE_COLUMN_USER_ID = new Column("userId", "INT");
    static final Column TABLE_COLUMN_DATE_ADDED= new Column("dateAdded", "DATE");
}
