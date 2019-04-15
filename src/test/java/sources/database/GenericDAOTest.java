package sources.database;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class GenericDAOTest {
    private GenericDAO<Integer> genericDAO;

    @Before
    public void setUp() throws Exception {
        genericDAO = new GenericDAO<Integer>(null) {
            @Override
            List<Integer> getRows() throws SQLException {
                return null;
            }

            @Override
            boolean createTable() throws SQLException {
                return false;
            }

            @Override
            boolean deleteRow(int id) throws SQLException {
                return false;
            }

            @Override
            boolean createRow(Integer value) throws SQLException {
                return false;
            }

            @Override
            boolean dropTable() throws SQLException {
                return false;
            }

            @Override
            boolean editRow(Integer id, Integer value) throws SQLException {
                return false;
            }

            @Override
            boolean deleteAllRows() throws SQLException {
                return false;
            }
        };
    }

    @Test
    public void tableCreatorNull() {
        String expected = null;
        String generatedSQL;
        generatedSQL = genericDAO.tableCreator(null);
        assertNull(generatedSQL);
        generatedSQL = genericDAO.tableCreator(null, new Column("", ""));
        assertNull(generatedSQL);
    }

    @Test
    public void tableEdit() {
        String expected = "UPDATE Students SET first_name=?, last_name=? WHERE id=678;";
        String generatedSQL = genericDAO.preparedEdit("Students", "678", new Column("id", ""), new Column("first_name", ""), new Column("last_name", ""));
        assertEquals(expected, generatedSQL);
    }

    @Test
    public void tableCreatorTableCreation() {
        String sql = "CREATE TABLE IF NOT EXISTS MYTABLE(" ;
        sql += "CATEGORY VARCHAR(300), ";
        sql += "LEARNING INT PRIMARY KEY AUTO_INCREMENT";
        sql += ");";
        String generatedSQL = genericDAO.tableCreator("MYTABLE", new Column("CATEGORY", "VARCHAR(300)"), new Column("LEARNING", "INT PRIMARY KEY AUTO_INCREMENT"));
        assertEquals(sql, generatedSQL);
    }

    @Test
    public void preparedInsert() {
        String expected = "INSERT INTO MYTABLE(COL1, COL2) VALUES(?, ?)";
        String sql = genericDAO.preparedInsert("MYTABLE", new Column("COL1", "VARCHAR()"), new Column("COL2", "Integer"));
        assertEquals(expected, sql);
        sql = genericDAO.preparedInsert("MYTABLE");
        assertNull(sql);
    }
}