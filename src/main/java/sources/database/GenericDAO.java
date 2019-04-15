package sources.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.*;

abstract class GenericDAO<E> {
    private final Connection connection;

    public Connection getConnection() {
        return connection;
    }

    GenericDAO(Connection connection) {
        this.connection = connection;
    }

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

    abstract List<E> getRows() throws SQLException;

    abstract boolean createTable() throws SQLException;

    abstract boolean deleteRow(int id) throws SQLException;

    abstract boolean createRow(E value) throws SQLException;

    abstract boolean dropTable() throws SQLException;

    abstract boolean editRow(Integer id, E value) throws SQLException;

    abstract boolean deleteAllRows() throws SQLException;
}