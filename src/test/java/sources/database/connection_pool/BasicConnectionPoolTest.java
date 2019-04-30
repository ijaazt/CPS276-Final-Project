package sources.database.connection_pool;

import exceptions.TooManyConnectionsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import static info.DatabaseInfo.*;

class BasicConnectionPoolTest {
    private ConnectionPool basicConnectionPool;
    @BeforeEach void setup() throws SQLException {
        basicConnectionPool = BasicConnectionPool.createPool(URL.info(), USERNAME.info(), PASSWORD.info());
    }

    @Test
    void createBadConnection() throws SQLException {
        assertThrows(SQLException.class, ()-> {
            basicConnectionPool = BasicConnectionPool.createPool(null, USERNAME.info(), PASSWORD.info());
        });
    }
    @Test void connectionIsNotNull() throws TooManyConnectionsException {
        assertNotNull(basicConnectionPool.getConnection());
    }
    @Test
    void whenTooManConnections() throws TooManyConnectionsException {
        assertThrows(TooManyConnectionsException.class, ()->{ while(basicConnectionPool.getConnection() != null);}
    );
    }

    @Test
    void closedConnections() throws TooManyConnectionsException, SQLException {
        assertThrows(Exception.class, () ->{
            basicConnectionPool.closeAllConnections();
            basicConnectionPool.getConnection().createStatement().execute("");
        });
    }
    @Test void addsConnections() throws TooManyConnectionsException {
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
    @Test void releasingConnection() throws TooManyConnectionsException {
        Connection connection = basicConnectionPool.getConnection();
        int unusedConnectionLimit = basicConnectionPool.getAvailableConnectionLimit();
        basicConnectionPool.releaseConnection(connection);
        assertEquals(unusedConnectionLimit + 1, basicConnectionPool.getAvailableConnectionLimit());
    }
}
