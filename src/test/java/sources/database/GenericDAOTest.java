package sources.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class GenericDAOTest {
    private static GenericDAO<Integer> genericDAO;

    @BeforeAll
    static void setUp() throws Exception {
        genericDAO = new GenericDAO<Integer>(null) {
            @Override
            public List<Integer> getRows() throws SQLException {
                return null;
            }

            @Override
            public void createTable() throws SQLException {
            }

            @Override
            public void deleteRow(int id) throws SQLException {
            }

            @Override
            public void createRow(Integer value) throws SQLException {
            }

            @Override
            public void dropTable() throws SQLException {
            }

            @Override
            public void editRow(Integer id, Integer value) throws SQLException {
            }

            @Override
            public void deleteAllRows() throws SQLException {
            }
        };
    }

    @Test
    void tableCreatorNull() {
        String expected = null;
        String generatedSQL;
        generatedSQL = genericDAO.tableCreator(null);
        assertNull(generatedSQL);
        generatedSQL = genericDAO.tableCreator(null, new Column("", ""));
        assertNull(generatedSQL);
    }

    @Test
    void tableEdit() {
        String expected = "UPDATE Students SET first_name=?, last_name=? WHERE id=678;";
        String generatedSQL = genericDAO.preparedEdit("Students", "678", new Column("id", ""), new Column("first_name", ""), new Column("last_name", ""));
        assertEquals(expected, generatedSQL);
    }

    @Test
    void tableCreatorTableCreation() {
        String sql = "CREATE TABLE IF NOT EXISTS MYTABLE(" ;
        sql += "CATEGORY VARCHAR(300), ";
        sql += "LEARNING INT PRIMARY KEY AUTO_INCREMENT";
        sql += ");";
        String generatedSQL = genericDAO.tableCreator("MYTABLE", new Column("CATEGORY", "VARCHAR(300)"), new Column("LEARNING", "INT PRIMARY KEY AUTO_INCREMENT"));
        assertEquals(sql, generatedSQL);
    }

    @Test
    void preparedInsert() {
        String expected = "INSERT INTO MYTABLE(COL1, COL2) VALUES(?, ?)";
        String sql = genericDAO.preparedInsert("MYTABLE", new Column("COL1", "VARCHAR()"), new Column("COL2", "Integer"));
        assertEquals(expected, sql);
        sql = genericDAO.preparedInsert("MYTABLE");
        assertNull(sql);
    }
}