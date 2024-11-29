package com.projet.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.projet.database.IncorrectCredentialsException;
import com.projet.database.SqlUtility;
import com.projet.database.UserAlreadyExistsException;


public abstract class AbstractUser {
    public int id;
    public String username;
    private String password;
    public UserRole role;

    protected AbstractUser(int id, String username, String password, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    protected AbstractUser(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    protected AbstractUser(int id, String username, UserRole role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public abstract PostType getMainListPostType();
    public abstract PostType getCreatePostType();
    public abstract boolean canValidateMissions();

    private static AbstractUser resultFunction(ResultSet result) throws SQLException {
        if (result.getString("role").equals(UserRole.USER_IN_NEED.toString())) {
            return new UserInNeed(
                result.getInt("id"),
                result.getString("username"),
                result.getString("password")
            );
        } else if (result.getString("role").equals(UserRole.VOLUNTEER.toString())) {
            return new Volunteer(
                result.getInt("id"),
                result.getString("username"),
                result.getString("password")
            );
        } else {
            return new Validator(
                result.getInt("id"),
                result.getString("username"),
                result.getString("password")
            );
        }
    }

    //methode to create a new line in the database when the signup button is clicked
    public void toDB() throws SQLException, UserAlreadyExistsException {
        try {
            ResultSet generatedKeys = SqlUtility.executeUpdate(
                "INSERT INTO users (username, password, role) VALUES (?, ?, ?)",
                username, password, role.toString()
            );
            if (!generatedKeys.next()) {
                throw new SQLException("No key generated.");
            }
            id = generatedKeys.getInt(1);
        } catch (SQLIntegrityConstraintViolationException e) {
            if (e.getErrorCode() == 1062) {
                throw new UserAlreadyExistsException("User already exists");
            }
            throw e;
        }
    }

    public static AbstractUser loginFromDB(String username, String password) throws SQLException, IncorrectCredentialsException {
        List<? extends AbstractUser> users = SqlUtility.executeQuery(
            "SELECT * FROM users WHERE username = ? AND password = ?",
            AbstractUser::resultFunction,
            username, password
        );
        if (users.size() == 0) {
            throw new IncorrectCredentialsException("Incorrect credentials");
        }
        return users.get(0);
    }

    public static AbstractUser getFromDatabase(int id) throws SQLException {
        List<? extends AbstractUser> users = SqlUtility.executeQuery(
            "SELECT * FROM users WHERE id = ?",
            AbstractUser::resultFunction,
            id
        );
        if (users.size() == 0) {
            return null;
        }
        return users.get(0);
    }
}
