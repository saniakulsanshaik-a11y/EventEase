package com.college.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521/XE";
    private static final String USER = "system";
    private static final String PASS = "sania";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Oracle driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}