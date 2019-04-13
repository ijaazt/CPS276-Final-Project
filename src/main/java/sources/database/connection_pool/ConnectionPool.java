package sources.database.connection_pool;

import exceptions.TooManyConnectionsException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection() throws TooManyConnectionsException;
    boolean releaseConnection(Connection connection);
    String getUrl();
    String getUser();
    String getPassword();
    int getMaxConnection();
    void increaseMaxConnection(int max);
    void closeAllConnections() throws SQLException;
    int getAvailableConnectionLimit();
    int getUsedConnectionLimit();
}
