package util;

import org.sqlite.SQLiteConfig;

import java.sql.*;

public class ConnectionFactory {

    public static Connection getConnection(String path) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + path; // db parameters
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true); //SQLite does not support foreign keys by default
            conn = DriverManager.getConnection(url, config.toProperties()); // create a connection to the database
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fechar conexão nula", e);
        }
    }

    public static void closeConnection(Connection connection, PreparedStatement statement) {
        try {
            if (connection != null) {
                connection.close();
            }

            if (statement != null) {
                statement.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fechar conexão ou statement nulos", e);
        }
    }

    public static void closeConnection(
            Connection connection,
            PreparedStatement statement,
            ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fechar conexão, statement ou resultSet nulos", e);
        }
    }

}
