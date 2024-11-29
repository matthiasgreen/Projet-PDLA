package com.projet.models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

import com.projet.database.DatabaseConnection;
import com.projet.database.IncorrectCredentialsException;
import com.projet.database.UserAlreadyExistsException;

public class UserInNeedTest {
    private Connection dbConnection;
    UserInNeed user;

    @Before
    public void setUp() throws SQLException {
        dbConnection = DatabaseConnection.getConnection();
        // Empty the users table
        // Diable foreign key checks to avoid errors
        dbConnection.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        dbConnection.prepareStatement("DELETE FROM users").executeUpdate();
        dbConnection.prepareStatement("DELETE FROM posts").executeUpdate();
        dbConnection.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
        user = new UserInNeed("test", "test");
    }

    @AfterAll
    public void tearDown() throws SQLException {
        dbConnection.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        dbConnection.prepareStatement("DELETE FROM users").executeUpdate();
        dbConnection.prepareStatement("DELETE FROM posts").executeUpdate();
        dbConnection.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

    @Test
    public void testToDB() throws SQLException, UserAlreadyExistsException {
        user.toDB();
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testToDBAlreadyExists() throws SQLException, UserAlreadyExistsException {
        user.toDB();
        user.toDB();
    }

    @Test
    public void testLoginFromDB() throws SQLException, UserAlreadyExistsException, IncorrectCredentialsException {
        user.toDB();
        UserInNeed user2 = (UserInNeed) UserInNeed.loginFromDB("test", "test");
        assertNotNull(user2);
        assertEquals(user.id, user2.id);
        assertEquals(user.username, user2.username);
        assertEquals(user.role, user2.role);
    }

    @Test(expected = IncorrectCredentialsException.class)
    public void testLoginFromDBIncorrect() throws SQLException, UserAlreadyExistsException, IncorrectCredentialsException {
        user.toDB();
        UserInNeed.loginFromDB("test", "test2");
    }

    @Test
    public void testGetFromDatabase() throws SQLException, UserAlreadyExistsException {
        user.toDB();
        int id = user.id;
        UserInNeed user2 = (UserInNeed) UserInNeed.getFromDatabase(id);
        assertNotNull(user2);
        assertEquals(user2.id, id);
        assertEquals(user2.username, user.username);
        assertEquals(user2.role, user.role);
        UserInNeed userNull = (UserInNeed) UserInNeed.getFromDatabase(-1);
        assertNull(userNull);
    }
}