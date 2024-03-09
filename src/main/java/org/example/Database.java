package org.example;


import java.sql.*;

public class Database {

    private static final Database INSTANCE = new Database();
    private static Connection connection;

    private Database() {
        String jdbcUrl = "jdbc:postgresql://localhost:32768/ladatabase";
        String username = "postgres";
        String password = "123";

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Connected yupi");
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public static Connection getConnection() {
        return connection;
    }

    public static int executeUpdate(String query) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot run query");
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}