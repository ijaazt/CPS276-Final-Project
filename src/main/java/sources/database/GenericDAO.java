package sources.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.util.stream.IntStream.*;

public abstract class GenericDAO<E> {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    GenericDAO(Connection connection) {
        this.connection = connection;
    }
    GenericDAO() { }

    String tableCreator(String tableName, Column... args) {
        if(args == null || tableName == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(tableName).append("(");
        for (int i = 0; i < args.length - 1; i++) {
            builder.append(args[i].getName()).append(" ").append(args[i].getType()).append(", ");
        }
        builder.append(args[args.length - 1].getName()).append(" ").append(args[args.length - 1].getType()).append(");");
        return builder.toString();
    }
    String preparedInsert(String tableName, Column... args) {
        if (args.length == 0)
            return null;
        StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + "(");
        range(0, args.length - 1).forEach(i -> sql.append(args[i].getName()).append(", "));
        sql.append(args[args.length - 1].getName()).append(")");
        sql.append(" VALUES").append("(");
        range(0, (args.length - 1)).mapToObj(i -> "?, ").forEach(sql::append);
        sql.append("?").append(")");
        return sql.toString();
    }

    String preparedEdit(String tableName, String query, Column... args) {
        StringBuilder sql = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
        range(1, args.length - 1).forEach(i -> sql.append(args[i].getName())
                .append("=?")
                .append(", "));
        sql
            .append(args[args.length - 1].getName()).append("=?")
            .append(" WHERE ").append(args[0].getName()).append("=")
            .append(query).append(";");
        return sql.toString();
    }

    public abstract List<E> getRows() throws SQLException;

    public abstract void createTable() throws SQLException;

    public abstract void deleteRow(int id) throws SQLException;

    public abstract void createRow(E value) throws SQLException;

    public abstract void dropTable() throws SQLException;

    public abstract void editRow(Integer id, E value) throws SQLException;

    public abstract void deleteAllRows() throws SQLException;
}