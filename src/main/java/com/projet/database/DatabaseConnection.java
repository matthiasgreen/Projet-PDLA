package com.projet.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    private static Connection connection;

    //
    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            java.util.Properties prop = new java.util.Properties();
            prop.load(input);
            URL = prop.getProperty("database.url");
            USER = prop.getProperty("database.user");
            PASSWORD = prop.getProperty("database.password");
        } catch (Exception e) {
            URL = "jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_004";
            USER = "projet_gei_004";
            PASSWORD = "eedi2AhJ";
        }
    }

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
}
