package sources.database.connection_pool;

import exceptions.TooManyConnectionsException;
import model.Learning;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sources.database.UserDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static info.DatabaseInfo.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTest {
    private UserDAO userDAO;
    private ConnectionPool basicConnectionPool;
    private Connection currentConnection;
    private List<User> expected;

    @Test
    void droppingConnections() throws SQLException {
        assertThrows(SQLException.class, ()-> {
            userDAO.createTable();
            userDAO.dropTable();
            userDAO.getRows();
        });
    }

    @BeforeEach
    void setup() throws TooManyConnectionsException, SQLException {
        basicConnectionPool = BasicConnectionPool.createPool(URL.info(), USERNAME.info(), PASSWORD.info());
        currentConnection = basicConnectionPool.getConnection();
        userDAO = new UserDAO(basicConnectionPool.getConnection());
        userDAO.dropTable();
        userDAO.createTable();
        expected = new ArrayList<>();
    }

    @AfterEach
    void cleanup() {
        basicConnectionPool.releaseConnection(currentConnection);
    }

    @Test
    void basicReturn() throws SQLException, TooManyConnectionsException, ParseException {
        expected.add(new User("ijaaz", "testingisgreat", "Muhammad", "Tello", 1));
        userDAO.createRow(expected.get(0));
        List<User> actual = userDAO.getRows();
        assertEquals(expected, actual);
    }

    @Test
    void addingNullId() throws SQLException {
        //public Learning(String category, String learning, Date date, int id, int userId) {
        User user = new User("programming", "testingisbad", "Muhammad", "Tell", null);
        User user1 = new User("programming", "testingisbad", "Muhammad", "Tell", null);
        userDAO.createRow(user);
        userDAO.createRow(user1);
        user.setId(1);
        user1.setId(2);
        expected.add(user);
        expected.add(user1);
        assertEquals(expected, userDAO.getRows());
    }

    @Test
    void removingFromDB() throws SQLException {
        User user = new User("programming", "testingisbad", "Muhammad", "Tello", 1);
        expected.add(user);
        userDAO.createRow(user);
        expected.clear();
        userDAO.deleteRow(1);
        assertEquals(expected, userDAO.getRows());
    }

    @Test
    void modifyRow() throws SQLException {
        User user = new User("programming", "testingisbad", "Muhammad", "Tello", 1);
        User user1 = new User("testing", "testingisbad", "Muhammad", "Tello", 1);
        userDAO.createRow(user);
        userDAO.editRow(1, user1);
        User user2 = userDAO.getRows().get(0);
        assertEquals(user1, user2);
    }

    @Test
    void removingAllFromDB() throws SQLException {
        User user = new User("programming", "testingisbad", "Muhammad", "Tell", 1);
        User user1 = new User("programming", "testingisbad", "Muhammad", "Tell", 2);
        userDAO.createRow(user);
        userDAO.createRow(user1);
        expected.add(user);
        expected.add(user1);
        expected.clear();
        userDAO.deleteAllRows();
        assertEquals(expected, userDAO.getRows());
    }

}
