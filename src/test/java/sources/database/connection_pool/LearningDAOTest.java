package sources.database.connection_pool;

import exceptions.TooManyConnectionsException;
import model.Learning;
import org.junit.jupiter.api.*;
import sources.database.LearningDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static info.DatabaseInfo.*;
import static org.junit.jupiter.api.Assertions.*;

class LearningDAOTest {
    private LearningDAO learningDAO;
    private ConnectionPool basicConnectionPool;
    private Connection currentConnection;
    private List<Learning> expected;

    @Test
    void droppingConnections() throws SQLException {
        assertThrows(SQLException.class, ()-> {
            learningDAO.createTable();
            learningDAO.dropTable();
            learningDAO.getRows();
        });
    }
  @Test
    void modifyRow() throws SQLException {
      Learning learning = new Learning("programming", "testing is bad", LocalDate.parse("2018-03-12"), 1, 1);
      Learning learning1 = new Learning("testing", "testing is bad", LocalDate.parse("2018-03-12"), 1, 1);
        learningDAO.createRow(learning);
        learningDAO.editRow(1, learning1);
        Learning learning2 = learningDAO.getRows().get(0);
        assertEquals(learning1, learning2);
    }

    @BeforeEach
    void setup() throws TooManyConnectionsException, SQLException {
        basicConnectionPool = BasicConnectionPool.createPool(URL.info(), USERNAME.info(), PASSWORD.info());
        currentConnection = basicConnectionPool.getConnection();
        learningDAO = new LearningDAO(basicConnectionPool.getConnection());
        learningDAO.dropTable();
        learningDAO.createTable();
        expected = new ArrayList<>();
    }

    @AfterEach
    void cleanup() {
        basicConnectionPool.releaseConnection(currentConnection);
    }

    @Test
    void basicReturn() throws SQLException, TooManyConnectionsException, ParseException {
        expected.add(new Learning("programming", "testing is great", LocalDate.now(), 1, 1));
        learningDAO.createRow(expected.get(0));
        List<Learning> actual = learningDAO.getRows();
        assertEquals(expected, actual);
    }

    @Test
    void addingNullId() throws SQLException {
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
    void removingFromDB() throws SQLException {
        Learning learning = new Learning("programming", "testing is bad", LocalDate.parse("2018-03-12"), null, 1);
        expected.add(learning);
        learningDAO.createRow(learning);
        expected.clear();
        learningDAO.deleteRow(1);
        assertEquals(expected, learningDAO.getRows());
    }

    @Test
    void removingAllFromDB() throws SQLException {
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
