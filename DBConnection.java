package com.Ordermanagment1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/ordermanagment";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "qualian";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
