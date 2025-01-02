package com.example.ecommerce_java_proj.backend;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database_connection {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/java";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = dotenv.get("DATABASE_PASSWORD"); // Now static
    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private database_connection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                throw new SQLException("Error while connecting to the database.", e);
            }
        }
        return connection;
    }
//
//    public static void closeConnection() throws SQLException {
//        if (connection != null && !connection.isClosed()) {
//            connection.close();
//        }
//    }
}
