package controller;

import exceptions.TooManyConnectionsException;
import model.Learning;
import model.User;
import sources.database.GenericDAO;
import sources.database.LearningDAO;
import sources.database.UserDAO;
import sources.database.connection_pool.ConnectionPool;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Repository {
    private final ConnectionPool connectionPool;
    private final LearningDAO learningDAO;
    private final UserDAO userDAO;

    public void shutdown() {
        try {
            connectionPool.closeAllConnections();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Repository(ConnectionPool connectionPool, LearningDAO learningDAO, UserDAO userDAO) {
        this.connectionPool = connectionPool;
        this.learningDAO = learningDAO;
        this.userDAO = userDAO;
    }

    public void clear() {
        basicClear(learningDAO, connectionPool);
        basicClear(userDAO, connectionPool);
    }
     private void basicClear(GenericDAO dao, ConnectionPool pool) {
        setUpDAO(dao, pool);
        try {
            dao.dropTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tearDownDAO(dao, pool);
    }

    private void basicSetup(GenericDAO dao, ConnectionPool pool) {
        setUpDAO(dao, pool);
        try {
            dao.createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tearDownDAO(dao, pool);
    }
    void setup() {
        basicSetup(learningDAO, connectionPool);
        basicSetup(userDAO, connectionPool);
    }

    public void setUpDAO(GenericDAO genericDAO, ConnectionPool connectionPool) {
        try {
            genericDAO.setConnection(connectionPool.getConnection());
        } catch (TooManyConnectionsException e) {
            e.printStackTrace();
        }
    }

    public void tearDownDAO(GenericDAO genericDAO, ConnectionPool connectionPool) {
        connectionPool.releaseConnection(genericDAO.getConnection());
        genericDAO.setConnection(null);
    }

    public void addLearning(Learning learning) {
        if (!hasUser(learning.getUserId())) {
            return;
        }
        add(learning, connectionPool, learningDAO);
    }
    List get(GenericDAO dao, ConnectionPool connectionPool) {
        List objects = Collections.emptyList();
        setUpDAO(dao, connectionPool);
        try {
            objects = dao.getRows();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tearDownDAO(dao, connectionPool);
        return objects;
    }
    public List<Learning> getLearning() {
        return get(learningDAO, connectionPool);
    }

    public List<User> getUsers() {
        return get(userDAO, connectionPool);
    }

    boolean hasUser(int userId) {
        final boolean[] returnVal = {false};
        for (User user : getUsers()) {
            if (user.getId() == userId) {
                returnVal[0] = true;
            }
        }
        return returnVal[0];
    }

    void add(Object object, ConnectionPool connectionPool, GenericDAO dao) {
        setUpDAO(dao, connectionPool);
        try {
            dao.createRow(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tearDownDAO(dao, connectionPool);
    }
    void addUser(User user) {
        add(user, connectionPool, userDAO);
    }

    public void delete(Integer id, ConnectionPool connectionPool, GenericDAO dao) {
        setUpDAO(dao, connectionPool);
            try {
                if(id == null) {
                    dao.deleteAllRows();
                } else {
                    dao.deleteRow(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
        }
        tearDownDAO(dao, connectionPool);
    }
    public void modify(Integer id, Object object, ConnectionPool connectionPool, GenericDAO dao) {
        setUpDAO(dao, connectionPool);
        try {
            dao.editRow(id, object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tearDownDAO(dao, connectionPool);
    }
    void deleteUser(Integer id) {
        delete(id, connectionPool, userDAO);
    }
    void deleteLearning(Integer id){
        delete(id, connectionPool, learningDAO);
    }

    void modifyUser(Integer id, User user) {
        modify(id, user, connectionPool, userDAO);
    }

    void modifyLearning(Integer id, Learning learning) {
        modify(id, learning, connectionPool, learningDAO);
    }
}
