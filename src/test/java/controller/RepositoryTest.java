package controller;

import info.DatabaseInfo;
import model.Learning;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sources.database.LearningDAO;
import sources.database.UserDAO;
import sources.database.connection_pool.BasicConnectionPool;
import sources.database.connection_pool.ConnectionPool;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class RepositoryTest {
    private Repository repository;
    private UserDAO userDAO;
    private LearningDAO learningDAO;
    private ConnectionPool connectionPool;

    @Before
    public void setUp() throws Exception {
        userDAO = new UserDAO();
        learningDAO = new LearningDAO();
        connectionPool = BasicConnectionPool.createPool(DatabaseInfo.URL.info(), DatabaseInfo.USERNAME.info(), DatabaseInfo.PASSWORD.info());
        repository = new Repository(connectionPool, learningDAO, userDAO);
        repository.setup();
    }

    @After
    public void tearDown() throws Exception {
        repository.clear();
    }

    @Test
    public void upAndTear() {
        repository.setUpDAO(userDAO, connectionPool);
        assertNotNull(userDAO.getConnection());
        repository.tearDownDAO(userDAO, connectionPool);
        assertNull(userDAO.getConnection());
    }
    @Test
    public void addingLearningWithoutUser() {
        Learning learning = new Learning("cat", "learn", LocalDate.now(), null, 1);
        repository.addLearning(learning);
        assertEquals(repository.getUsers().size(), 0);
    }
    @Test
    public void addingLearningWithUser() {
        Learning learning = new Learning("cat", "learn", LocalDate.now(), 1, 1);
        User user = new User("us", "pass", "firs", "last", 1);
        repository.addUser(user);
        repository.addLearning(learning);
        assertEquals(user, repository.getUsers().get(0));
        assertEquals(learning, repository.getLearning().get(0));
        //assertTrue(addingLearning);
    }
    @Test
    public void checkingUser() {
        User user = new User("us", "pass", "firs", "last", 1);
        repository.addUser(user);
        assertTrue(repository.hasUser(user.getId()));
        assertFalse(repository.hasUser(2));
    }
    @Test
    public void addingGettingStuff() {
        User user = new User("us", "pass", "firs", "last", 1);
        repository.add(user, connectionPool, userDAO);
        List<User> users= repository.get(userDAO, connectionPool);
        assertEquals(user, users.get(0));
    }

    @Test
    public void testDeleting() {
        Learning learning = new Learning("cat", "learn", LocalDate.now(), 1, 1);
        repository.add(learning, connectionPool, learningDAO);
        repository.delete(1, connectionPool, learningDAO);
        List<Learning> learnings = repository.get(learningDAO, connectionPool);
        assertEquals(learnings.size(), 0);
    }
    @Test
    public void testModifying() {
        Learning learning = new Learning("cat", "learn", LocalDate.now(), 1, 1);
        Learning learning1 = new Learning("catISGREAT", "learn", LocalDate.now(), 1, 1);
        repository.add(learning, connectionPool, learningDAO);
        repository.modify(1, learning1, connectionPool, learningDAO);
        List<Learning> learnings = repository.get(learningDAO, connectionPool);
        assertEquals(learning1, learnings.get(0));
    }
}