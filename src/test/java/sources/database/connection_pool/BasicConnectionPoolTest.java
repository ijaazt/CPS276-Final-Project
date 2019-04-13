package sources.database.connection_pool;

import exceptions.TooManyConnectionsException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLNonTransientConnectionException;

import static info.DatabaseInfo.*;

public class BasicConnectionPoolTest {
    ConnectionPool basicConnectionPool;
    @Before public  void setup() throws SQLException {
        basicConnectionPool = BasicConnectionPool.createPool(URL.info(), USERNAME.info(), PASSWORD.info());
    }

    @Test(expected = SQLException.class)
    public void createBadConnection() throws SQLException {
        basicConnectionPool = BasicConnectionPool.createPool(null, USERNAME.info(), PASSWORD.info());
    }
    @Test public void connectionIsNotNull() throws TooManyConnectionsException {
        assertNotNull(basicConnectionPool.getConnection());
    }
    @Test(expected = TooManyConnectionsException.class)
    public void whenTooManConnections() throws TooManyConnectionsException {
        while(basicConnectionPool.getConnection() != null);
    }

    @Test(expected = Exception.class)
    public void closedConnections() throws TooManyConnectionsException, SQLException {
        basicConnectionPool.closeAllConnections();
        basicConnectionPool.getConnection().createStatement().execute("");
    }
    @Test public void addsConnections() throws TooManyConnectionsException {
        int initialMax = basicConnectionPool.getMaxConnection();
        try {
            for (int i = 0; i < basicConnectionPool.getMaxConnection(); i++) {
                basicConnectionPool.getConnection();
            }
        } catch (TooManyConnectionsException e) {
            basicConnectionPool.increaseMaxConnection(20);
        }
        basicConnectionPool.getConnection();
        basicConnectionPool.getConnection();
        assertEquals(initialMax + 20, basicConnectionPool.getMaxConnection());
    }
    @Test public void releasingConnection() throws TooManyConnectionsException {
        Connection connection = basicConnectionPool.getConnection();
        int unsedConnectionLimit = basicConnectionPool.getAvailableConnectionLimit();
        basicConnectionPool.releaseConnection(connection);
        assertEquals(unsedConnectionLimit + 1, basicConnectionPool.getAvailableConnectionLimit());
    }
}
