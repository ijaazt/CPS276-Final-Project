package controller;

import exceptions.NoUserFound;
import exceptions.TooManyConnectionsException;
import model.Learning;
import model.User;
import sources.database.GenericDAO;
import sources.database.LearningDAO;
import sources.database.UserDAO;
import sources.database.connection_pool.ConnectionPool;
import views.model.Authentication;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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
        setup();
    }

    void clear() {
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

    <T extends GenericDAO> void setUpDAO(T genericDAO, ConnectionPool connectionPool) {
        try {
            genericDAO.setConnection(connectionPool.getConnection());
        } catch (TooManyConnectionsException e) {
            e.printStackTrace();
        }
    }

    <T extends GenericDAO> void tearDownDAO(T genericDAO, ConnectionPool connectionPool) {
        connectionPool.releaseConnection(genericDAO.getConnection());
        genericDAO.setConnection(null);
    }

    public void addLearning(Authentication authentication, Learning learning) throws NoUserFound {
            if(hasUser(authentication)) {
                add(learning, connectionPool, learningDAO);
            } else {
                throw new NoUserFound();
            }
    }

    private boolean hasUser(Authentication authentication) {
            final boolean[] returnVal = {false};
        for (User user : getUsers()) {
            if (user.getPassword().equals(authentication.getPassword()) && user.getUserName().equals(authentication.getUsername())) {
                returnVal[0] = true;
            }
        }
        return returnVal[0];
    }

    <T,S extends GenericDAO> List<T> get(S dao, ConnectionPool connectionPool) {
        List<T> objects = new LinkedList<>();
        setUpDAO(dao, connectionPool);
        try {
            objects = dao.getRows();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tearDownDAO(dao, connectionPool);
        return objects;
    }

    public List<Learning> getLearning(Authentication authentication) throws NoUserFound {
        List<Learning> learningList = get(learningDAO, connectionPool);
        User user = getUser(authentication);
        return learningList.stream().filter(learning -> learning.getUserId() == user.getId()).collect(Collectors.toCollection(ArrayList::new));
    }

    List<User> getUsers() {
        return get(userDAO, connectionPool);
    }

    public User getUser(String username, String password) throws NoUserFound {
            for(User user : getUsers()) {
                if(user.getPassword().equals(password) && user.getUserName().equals(username)) {
                    return user;
                }
            }
            throw new NoUserFound();
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

    <T, S extends GenericDAO> void add(T object, ConnectionPool connectionPool, S dao) {
        setUpDAO(dao, connectionPool);
        try {
            dao.createRow(object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tearDownDAO(dao, connectionPool);
    }
    public void addUser(User user) {
        add(user, connectionPool, userDAO);
    }

    void delete(Integer id, ConnectionPool connectionPool, GenericDAO dao) {
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
    void modify(Integer id, Object object, ConnectionPool connectionPool, GenericDAO dao) {
        setUpDAO(dao, connectionPool);
        try {
            dao.editRow(id, object);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tearDownDAO(dao, connectionPool);
    }
    public void deleteUser(Integer id) {
        delete(id, connectionPool, userDAO);
    }
    public void deleteLearning(Integer id, Authentication authentication){
        if(id == null) {
            getUsers().stream().filter(user -> user.getUserName().equals(authentication.getUsername()) && user.getPassword().equals(authentication.getPassword())).forEach(user -> delete(user.getId(), connectionPool, learningDAO));
        }
        delete(id, connectionPool, learningDAO);
    }

    public void modifyUser(Integer id, User user) {
        modify(id, user, connectionPool, userDAO);
    }

    public void modifyLearning(Authentication authentication, Learning learning) throws NoUserFound {
            getLearning(authentication).forEach(learning1 -> {
                if(learning1.getId().equals(learning.getId())) {
                    modify(learning1.getId(), learning, connectionPool, learningDAO);
                }
            });
    }

    public User getUser(Authentication parameterValue) throws NoUserFound {
        return getUser(parameterValue.getUsername(), parameterValue.getPassword());
    }
}
