package com.projet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/";
    private static final String USER = "projet_gei_004";
    private static final String PASSWORD = "eedi2AhJ";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.err.println("FATAL: failed to connect to database");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
