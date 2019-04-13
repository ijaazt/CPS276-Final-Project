package views;

import sources.database.connection_pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

import static info.DatabaseInfo.*;
import static info.ServletInfo.CONNECTION_POOL;
import static sources.database.connection_pool.BasicConnectionPool.createPool;

@WebListener
public class PooledDatabaseStartup implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName(DRIVER_CLASS_NAME.info()).newInstance();
            ConnectionPool connectionPool = createPool(URL.info(), USERNAME.info(), PASSWORD.info());
            sce.getServletContext().setAttribute(CONNECTION_POOL.info(), connectionPool);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool pool = (ConnectionPool) sce.getServletContext().getAttribute(CONNECTION_POOL.info());
        if(pool != null) {
            try {
                pool.closeAllConnections();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
