package views;

import com.sun.corba.se.spi.activation.RepositoryOperations;
import controller.Repository;
import sources.database.LearningDAO;
import sources.database.UserDAO;
import sources.database.connection_pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

import static info.DatabaseInfo.*;
import static info.ServletInfo.REPOSITORY;
import static sources.database.connection_pool.BasicConnectionPool.createPool;

@WebListener
public class PooledDatabaseStartup implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName(DRIVER_CLASS_NAME.info()).newInstance();
            ConnectionPool connectionPool = createPool(URL.info(), USERNAME.info(), PASSWORD.info());
            Repository repository = new Repository(connectionPool, new LearningDAO(), new UserDAO());
            //Repository(ConnectionPool connectionPool, LearningDAO learningDAO, UserDAO userDAO) {
            sce.getServletContext().setAttribute(REPOSITORY.info(), repository);
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

        @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Repository repository = (Repository) sce.getServletContext().getAttribute(REPOSITORY.info());
        repository.shutdown();
    }
}
