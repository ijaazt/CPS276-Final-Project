package sources.database.connection_pool;

import exceptions.TooManyConnectionsException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
public class BasicConnectionPool implements ConnectionPool {
    private final String url;
    private final String user;
    private final String password;
    private final List<Connection> unUsedConnections;
    private final List<Connection> usedConnections;
    private static final int INITIAL_CONNECTION_LIMIT = 20;
    private int maxConnection = INITIAL_CONNECTION_LIMIT;

    public static BasicConnectionPool createPool(String url, String user, String password) throws SQLException {
        List<Connection> connectionList = new ArrayList<>();
        for (int i = 0; i < INITIAL_CONNECTION_LIMIT; i++) {
             connectionList.add(createConnection(url, user, password));
        }
        return new BasicConnectionPool(url, user, password, connectionList);
    }

    private static Connection createConnection(String url, String user, String password) throws SQLException {
        return DriverManager.getConnection(url+"?&serverTimezone=GMT", user, password);
    }

    private BasicConnectionPool(String url, String user, String password, List<Connection> connections) {
        this.url = url;
        this.user = user;
        this.password  = password;
        this.unUsedConnections = connections;
        this.usedConnections = new ArrayList<>();
    }


    @Override
    public void closeAllConnections() throws SQLException {
        for (Connection unUsedConnection : unUsedConnections) {
            unUsedConnection.close();
        }
        for (Connection usedConnection: usedConnections) {
            usedConnection.close();
        }
    }

    @Override
    public int getAvailableConnectionLimit() {
        return unUsedConnections.size();
    }

    @Override
    public int getUsedConnectionLimit() {
        return usedConnections.size();
    }

    @Override
    synchronized public Connection getConnection() throws TooManyConnectionsException {
        int connectionPointer = unUsedConnections.size() - 1;
        if(connectionPointer < 1) {
            throw new TooManyConnectionsException();
        }
        Connection currentConnection = unUsedConnections.remove(connectionPointer);
        usedConnections.add(currentConnection);
        return currentConnection; }

    @Override
    synchronized public boolean releaseConnection(Connection connection) {
        boolean connectionIsBeingUsed = usedConnections.contains(connection);
        if(connectionIsBeingUsed) {
            int connectionPointer = usedConnections.indexOf(connection);
            Connection connectionToBeReleased = usedConnections.remove(connectionPointer);
            return unUsedConnections.add(connectionToBeReleased);
        } else {
            return false;
        }
    }

    synchronized public void increaseMaxConnection(int limit) {
        this.maxConnection += limit;
        for(int i = 0; i < limit; i++) {
            try {
                unUsedConnections.add(createConnection(url, user, password));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int getMaxConnection() {
        return maxConnection;
    }
}
