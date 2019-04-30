package sources.database;

import model.Learning;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static sources.database.LearningContract.*;

public class LearningDAO extends GenericDAO<Learning> {

    public LearningDAO(Connection connection) {
        super(connection);
    }

    public LearningDAO() {

    }

    public void createTable() throws SQLException {
        String sql = tableCreator(TABLE_NAME, TABLE_COLUMN_CATEGORY, TABLE_COLUMN_LEARNING, TABLE_COLUMN_DATE_ADDED, TABLE_COLUMN_ID, TABLE_COLUMN_USER_ID);
        getConnection().createStatement().execute(sql);
    }


    @Override
    public List<Learning> getRows() throws SQLException {
        List<Learning> results = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + ";";
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Learning learning = new Learning(
                        resultSet.getString(TABLE_COLUMN_CATEGORY.getName()),
                        resultSet.getString(TABLE_COLUMN_LEARNING.getName()),
                        LocalDate.parse(resultSet.getDate(TABLE_COLUMN_DATE_ADDED.getName()).toString()),
                        resultSet.getInt(TABLE_COLUMN_ID.getName()),
                        resultSet.getInt(TABLE_COLUMN_USER_ID.getName()));
                results.add(learning);
            }
        }
        return results;
    }

    @Override
    public void deleteRow(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + TABLE_COLUMN_ID.getName() + "=" + id;
        getConnection().createStatement().execute(sql);
    }

    @Override
    public void createRow(Learning value) throws SQLException {
        String sql = preparedInsert(TABLE_NAME, TABLE_COLUMN_USER_ID, TABLE_COLUMN_ID, TABLE_COLUMN_DATE_ADDED, TABLE_COLUMN_LEARNING, TABLE_COLUMN_CATEGORY);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setObject(1, value.getUserId());
            preparedStatement.setObject(2, value.getId());
            preparedStatement.setObject(3, value.getDate());
            preparedStatement.setObject(4, value.getLearning());
            preparedStatement.setObject(5, value.getCategory());
            preparedStatement.execute();
        }
    }

    @Override
    public void dropTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        getConnection().createStatement().execute(sql);
    }

    @Override
    public void editRow(Integer id, Learning value) throws SQLException {
        String sql = preparedEdit(TABLE_NAME, String.valueOf(id), TABLE_COLUMN_ID, TABLE_COLUMN_CATEGORY, TABLE_COLUMN_LEARNING, TABLE_COLUMN_DATE_ADDED);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setObject(1, value.getCategory());
            preparedStatement.setObject(2, value.getLearning());
            preparedStatement.setObject(3, value.getDate());
            preparedStatement.execute();
        }
    }

    @Override
    public void deleteAllRows() throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME;
        getConnection().createStatement().execute(sql);
    }
}
