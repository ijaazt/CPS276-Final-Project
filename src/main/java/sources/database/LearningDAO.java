package sources.database;

import model.Learning;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import static sources.database.LearningContract.*;

public class LearningDAO implements GenericDAO<Learning> {
    private Connection connection;
    public LearningDAO(Connection connection) {
        this.connection = connection;

    }

    public boolean createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME;
        sql += " (";
        sql += TABLE_COLUMN_CATEGORY + " VARCHAR(300), ";
        sql += TABLE_COLUMN_LEARNING + " VARCHAR(400), ";
        sql += TABLE_COLUMN_DATE_ADDED + " DATE, ";
        sql += TABLE_COLUMN_ID + " INT PRIMARY KEY AUTO_INCREMENT, ";
        sql += TABLE_COLUMN_USER_ID + " INT";
        sql += ");";
        return connection.createStatement().execute(sql);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Learning> getRows() throws SQLException {
        return null;
    }

    @Override
    public boolean deleteRow(int id) throws SQLException {
        return false;
    }

    @Override
    public boolean createRow(Learning value) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + "";
        return true;
    }

    @Override
    public boolean dropTable() throws SQLException {
        return false;
    }

    @Override
    public boolean editRow(int id, Learning value) throws SQLException {
        return false;
    }

    @Override
    public void deleteAllRows() {

    }
}
