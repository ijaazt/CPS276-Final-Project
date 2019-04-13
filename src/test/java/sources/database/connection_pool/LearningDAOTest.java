package sources.database.connection_pool;
import static info.DatabaseInfo.*;

import exceptions.TooManyConnectionsException;
import model.Learning;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sources.database.LearningContract;
import sources.database.LearningDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;
public class LearningDAOTest {
    private LearningDAO learningDAO;
    private ConnectionPool basicConnectionPool;
    private Connection currentConnection;

    public LearningDAOTest() throws SQLException {
        basicConnectionPool = BasicConnectionPool.createPool(URL.info(), USERNAME.info(), PASSWORD.info());
    }
    @Before public void setup() throws TooManyConnectionsException {
        currentConnection = basicConnectionPool.getConnection();
        learningDAO = new LearningDAO(basicConnectionPool.getConnection());
    }
    @After public void cleanup() {
        basicConnectionPool.releaseConnection(currentConnection);
    }

    @Test public void testTableIsCreated() throws SQLException, TooManyConnectionsException {
        boolean createdTable = learningDAO.createTable();
        basicConnectionPool.getConnection().createStatement().execute("SELECT 1 FROM " + LearningContract.TABLE_NAME + " LIMIT 1;");
    }
    @Test public void nothing() throws SQLException {
        Learning learning = new Learning("life", "Getting a haircut was nice", new Date(), 1, 1);
        boolean shouldBeTrue = learningDAO.createRow(learning);
        boolean shouldBeFalse = learningDAO.createRow(learning);
        assertTrue(shouldBeTrue);
        assertFalse(shouldBeFalse);
    }
}
