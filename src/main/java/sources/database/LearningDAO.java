package sources.database;

import model.Learning;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
        List<Learning> results = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + ";";
        try(Statement statement = connection.createStatement()) {
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()) {
                Learning learning = new Learning(
                        resultSet.getString(TABLE_COLUMN_CATEGORY),
                        resultSet.getString(TABLE_COLUMN_LEARNING),
                        LocalDate.parse(resultSet.getDate(TABLE_COLUMN_DATE_ADDED).toString()),
                        resultSet.getInt(TABLE_COLUMN_ID),
                        resultSet.getInt(TABLE_COLUMN_USER_ID));
                results.add(learning);
            }
        }
        return results;
    }

    @Override
    public boolean deleteRow(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + TABLE_COLUMN_ID + "=" + id;
        return connection.createStatement().execute(sql);
    }

    @Override
    public boolean createRow(Learning value) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + "(";
        sql += TABLE_COLUMN_USER_ID + ",";
        sql += TABLE_COLUMN_ID + ",";
        sql += TABLE_COLUMN_DATE_ADDED + ",";
        sql += TABLE_COLUMN_LEARNING + ",";
        sql += TABLE_COLUMN_CATEGORY + ",";
        sql += ") VALUES(?, ?, ?, ?, ?);";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, value.getUserId());
            preparedStatement.setObject(2, value.getId());
            preparedStatement.setObject(3, value.getDate());
            preparedStatement.setObject(4, value.getLearning());
            preparedStatement.setObject(5, value.getCategory());
            return preparedStatement.execute();
        }
    }

    @Override
    public boolean dropTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return  connection.createStatement().execute(sql);
    }

    @Override
    public boolean editRow(int id, Learning value) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteAllRows() throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME;
        return  connection.createStatement().execute(sql);
    }
}
