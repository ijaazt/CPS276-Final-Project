package sources.database;

import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static sources.database.UserContract.*;

public class UserDAO extends GenericDAO<User> {
    public UserDAO(Connection connection) {
        super(connection);
    }
    public UserDAO() {}

    @Override
    public List<User> getRows() throws SQLException {
        List<User> results = new ArrayList<>();
        String sql = "SELECT * FROM " + TABLE_NAME + ";";
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sql);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                User user = new User(
                        resultSet.getString(TABLE_COLUMN_USER_NAME.getName()),
                        resultSet.getString(TABLE_COLUMN_PASSWORD.getName()),
                        resultSet.getString(TABLE_COLUMN_FIRST_NAME.getName()),
                        resultSet.getString(TABLE_COLUMN_LAST_NAME.getName()),
                        resultSet.getInt(TABLE_COLUMN_ID.getName())
                );
                results.add(user);
            }
        }
        return results;
    }

    @Override
    public boolean createTable() throws SQLException {
        String sql = tableCreator(TABLE_NAME, TABLE_COLUMN_PASSWORD, TABLE_COLUMN_FIRST_NAME, TABLE_COLUMN_ID, TABLE_COLUMN_LAST_NAME, TABLE_COLUMN_USER_NAME);
        return getConnection().createStatement().execute(sql);
    }

    @Override
    public boolean deleteRow(int id) throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + TABLE_COLUMN_ID.getName() + "=" + id;
        return getConnection().createStatement().execute(sql);
    }

    @Override
    public void createRow(User value) throws SQLException {
        String sql = preparedInsert(TABLE_NAME, TABLE_COLUMN_ID, TABLE_COLUMN_USER_NAME, TABLE_COLUMN_FIRST_NAME, TABLE_COLUMN_LAST_NAME, TABLE_COLUMN_PASSWORD);
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setObject(1, value.getId());
            preparedStatement.setObject(2, value.getUserName());
            preparedStatement.setObject(3, value.getFirstName());
            preparedStatement.setObject(4, value.getLastName());
            preparedStatement.setObject(5, value.getPassword());
            preparedStatement.execute();
        }
    }

    @Override
    public boolean dropTable() throws SQLException {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        return getConnection().createStatement().execute(sql);
    }

    @Override
    public boolean editRow(Integer id, User value) throws SQLException {
           String sql = preparedEdit(TABLE_NAME, String.valueOf(id), TABLE_COLUMN_ID, TABLE_COLUMN_USER_NAME, TABLE_COLUMN_PASSWORD, TABLE_COLUMN_FIRST_NAME, TABLE_COLUMN_LAST_NAME);
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setObject(1, value.getUserName());
            preparedStatement.setObject(2, value.getPassword());
            preparedStatement.setObject(3, value.getFirstName());
            preparedStatement.setObject(4, value.getLastName());
            return preparedStatement.execute();
        }
    }

    @Override
    public boolean deleteAllRows() throws SQLException {
        String sql = "DELETE FROM " + TABLE_NAME;
        return getConnection().createStatement().execute(sql);
    }
}
