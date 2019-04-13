package sources.database.connection_pool;

import exceptions.TooManyConnectionsException;
import model.Learning;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sources.database.LearningDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static info.DatabaseInfo.*;
import static org.junit.Assert.assertEquals;

public class LearningDAOTest {
    private LearningDAO learningDAO;
    private ConnectionPool basicConnectionPool;
    private Connection currentConnection;
    private List<Learning> expected;

    @Test(expected = SQLException.class)
    public void droppingConnections() throws SQLException {
        learningDAO.createTable();
        learningDAO.dropTable();
        learningDAO.getRows();
    }

    @Before
    public void setup() throws TooManyConnectionsException, SQLException {
        basicConnectionPool = BasicConnectionPool.createPool(URL.info(), USERNAME.info(), PASSWORD.info());
        currentConnection = basicConnectionPool.getConnection();
        learningDAO = new LearningDAO(basicConnectionPool.getConnection());
        learningDAO.dropTable();
        learningDAO.createTable();
        expected = new ArrayList<>();
    }

    @After
    public void cleanup() {
        basicConnectionPool.releaseConnection(currentConnection);
    }

    @Test
    public void basicReturn() throws SQLException, TooManyConnectionsException, ParseException {
        expected.add(new Learning("programming", "testing is great", LocalDate.now(), 1, 1));
        learningDAO.createRow(expected.get(0));
        List<Learning> actual = learningDAO.getRows();
        assertEquals(expected, actual);
    }

    @Test
    public void addingNullId() throws SQLException {
        //public Learning(String category, String learning, Date date, int id, int userId) {
        Learning learning = new Learning("programming", "testing is bad", LocalDate.parse("2018-03-12"), null, 1);
        Learning learning2 = new Learning("programming", "testing is bad", LocalDate.parse("2018-03-12"), null, 1);
        learningDAO.createRow(learning);
        learningDAO.createRow(learning2);
        learning.setId(1);
        learning2.setId(2);
        expected.add(learning);
        expected.add(learning2);
        assertEquals(expected, learningDAO.getRows());
    }

    @Test
    public void removingFromDB() throws SQLException {
        Learning learning = new Learning("programming", "testing is bad", LocalDate.parse("2018-03-12"), null, 1);
        expected.add(learning);
        learningDAO.createRow(learning);
        expected.clear();
        learningDAO.deleteRow(1);
        assertEquals(expected, learningDAO.getRows());
    }

    @Test
    public void removingAllFromDB() throws SQLException {
        Learning learning = new Learning("programming", "testing is bad", LocalDate.parse("2018-03-12"), null, 1);
        Learning learning2 = new Learning("programming", "testing is bad", LocalDate.parse("2018-03-12"), null, 1);
        learningDAO.createRow(learning);
        learningDAO.createRow(learning2);
        expected.add(learning);
        expected.add(learning2);
        learningDAO.createRow(learning);
        expected.clear();
        learningDAO.deleteAllRows();
        assertEquals(expected, learningDAO.getRows());
    }

}
