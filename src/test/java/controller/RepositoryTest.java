package controller;

import exceptions.NoUserFound;
import info.DatabaseInfo;
import model.Learning;
import model.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import sources.database.LearningDAO;
import sources.database.UserDAO;
import sources.database.connection_pool.BasicConnectionPool;
import sources.database.connection_pool.ConnectionPool;
import views.model.Authentication;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class RepositoryTest {
    private Repository repository;
    private UserDAO userDAO;
    private LearningDAO learningDAO;
    private ConnectionPool connectionPool;

    @BeforeEach
    void setUp() throws Exception {
        userDAO = new UserDAO();
        learningDAO = new LearningDAO();
        connectionPool = BasicConnectionPool.createPool(DatabaseInfo.URL.info(), DatabaseInfo.USERNAME.info(), DatabaseInfo.PASSWORD.info());
        repository = new Repository(connectionPool, learningDAO, userDAO);
        repository.setup();
        repository.clear();
        repository.setup();
    }

    @AfterEach
    void tearDown() throws Exception {
        repository.clear();
    }

    @Test
    void upAndTear() {
        repository.setUpDAO(userDAO, connectionPool);
        assertNotNull(userDAO.getConnection());
        repository.tearDownDAO(userDAO, connectionPool);
        Assertions.assertNull(userDAO.getConnection());
    }
    @Test
    void addingLearningWithoutUser() {
        Learning learning = new Learning("cat", "learn", LocalDate.now(), null, 1);
        Authentication authentication = new Authentication("ijaaz", "pass");
        assertThrows(NoUserFound.class, () -> {
            repository.addLearning(authentication, learning);
        });
    }
    @Test
    void addingLearningWithUser() throws NoUserFound {
        Learning learning = new Learning("cat", "learn", LocalDate.now(), 1, 1);
        User user = new User("us", "pass", "firs", "last", 1);
        repository.addUser(user);
        Authentication authentication = new Authentication(user.getUserName(), user.getPassword());
        repository.addLearning(authentication, learning);
        assertEquals(user, repository.getUsers().get(0));
        assertEquals(learning, repository.getLearning(authentication).get(0));
        //assertTrue(addingLearning);
    }
    @Test
    void checkingUser() {
        User user = new User("us", "pass", "firs", "last", 1);
        repository.addUser(user);
        assertTrue(repository.hasUser(user.getId()));
        assertFalse(repository.hasUser(2));
    }
    @Test
    void addingGettingStuff() {
        User user = new User("us", "pass", "firs", "last", 1);
        repository.add(user, connectionPool, userDAO);
        List<User> users= repository.get(userDAO, connectionPool);
        assertEquals(user, users.get(0));
    }

    @Test
    void testDeleting() {
        Learning learning = new Learning("cat", "learn", LocalDate.now(), 1, 1);
        repository.add(learning, connectionPool, learningDAO);
        repository.delete(1, connectionPool, learningDAO);
        List<Learning> learnings = repository.get(learningDAO, connectionPool);
        assertEquals(learnings.size(), 0);
    }
    @Test
    void testModifying() {
        Learning learning = new Learning("cat", "learn", LocalDate.now(), 1, 1);
        Learning learning1 = new Learning("catISGREAT", "learn", LocalDate.now(), 1, 1);
        repository.add(learning, connectionPool, learningDAO);
        repository.modify(1, learning1, connectionPool, learningDAO);
        List<Learning> learnings = repository.get(learningDAO, connectionPool);
        assertEquals(learning1, learnings.get(0));
    }

    static Stream<Arguments> argumentsStream() {
        return Stream.of(Arguments.arguments("user", "pass", "ijaaz", "a1ed"));
    }
    @ParameterizedTest
    @MethodSource("argumentsStream")
    void  testGettingUsers(String userB, String passB, String userG, String passG) throws NoUserFound {
        repository.addUser(new User(userG, passG, "Tello", "Tello",null));
        assertDoesNotThrow(() -> repository.getUser(userG, passG));
        assertThrows(NoUserFound.class, () -> repository.getUser(userB, passB));
    }
}