package com.projet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseConnectionTest {
    @Test
    public void testConnection() {
        Connection connection = DatabaseConnection.getConnection();
        assertNotNull(connection);
    }

    @Test
    public void testQuery() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        assertNotNull(connection);
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT COUNT(*) FROM users");
        assertTrue(result.next());
        assertTrue(result.getInt(1) >= 0);
    }
}
