package sources.database;

import java.sql.SQLException;
import java.util.List;

public interface GenericDAO<E> {
    List<E> getRows() throws SQLException;

    boolean createTable() throws SQLException;

    boolean deleteRow(int id) throws SQLException;

    boolean createRow(E value) throws SQLException;

    boolean dropTable() throws SQLException;

    boolean editRow(int id, E value) throws SQLException;

    void deleteAllRows();
}