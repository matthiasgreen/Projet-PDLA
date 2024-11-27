package com.projet.models;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.projet.database.DatabaseConnection;
import com.projet.database.IncorrectCredentialsException;
import com.projet.database.UserAlreadyExistsException;

public class UserTest {

    private Connection dbConnection;

    @Before
    public void setUp() throws SQLException {
        dbConnection = DatabaseConnection.getConnection();
        // Empty the users table
        // Diable foreign key checks to avoid errors
        dbConnection.prepareStatement("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        dbConnection.prepareStatement("DELETE FROM users").executeUpdate();
        dbConnection.prepareStatement("DELETE FROM posts").executeUpdate();
        dbConnection.prepareStatement("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

    @Test
    public void testToDB() throws SQLException, UserAlreadyExistsException {
        User user = new User("test", "test", UserRole.USER);
        user.toDB();
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testToDBAlreadyExists() throws SQLException, UserAlreadyExistsException {
        User user = new User("test", "test", UserRole.USER);
        user.toDB();
        user.toDB();
    }

    @Test
    public void testLoginFromDB() throws SQLException, UserAlreadyExistsException, IncorrectCredentialsException {
        User user = new User("test", "test", UserRole.USER);
        user.toDB();
        User user2 = User.loginFromDB("test", "test");
        assert user2 != null;
        assert user2.username.equals("test");
        assert user2.role == UserRole.USER;
    }

    @Test(expected = IncorrectCredentialsException.class)
    public void testLoginFromDBIncorrect() throws SQLException, UserAlreadyExistsException, IncorrectCredentialsException {
        User user = new User("test", "test", UserRole.USER);
        user.toDB();
        User user2 = User.loginFromDB("test", "test2");
    }

    @Test
    public void testGetFromDatabase() throws SQLException, UserAlreadyExistsException {
        User user = new User("test", "test", UserRole.USER);
        user.toDB();
        int id = user.id;
        User user2 = User.getFromDatabase(id);
        assert user2 != null;
        assert user2.username.equals(user.username);
        assert user2.role == user.role;
        User userNull = User.getFromDatabase(-1);
        assert userNull == null;
    }
}