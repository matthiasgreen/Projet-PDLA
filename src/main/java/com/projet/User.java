package com.projet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class User {
    public int id;
    public String username;
    private String password;

    User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private static ResultSet queryUser(String username, String password) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
            "SELECT * FROM users WHERE username = ? AND password = ?"
        );
        statement.setString(1, username);
        statement.setString(2, password);
        return statement.executeQuery();
    }

    //methode to create a new line in the database when the signup button is clicked
    public void toDB() throws SQLException, UserAlreadyExistsException {
        Connection connection = DatabaseConnection.getConnection();
        PreparedStatement insertQuery = connection.prepareStatement(
            "INSERT INTO users (username, password) VALUES (?, ?)"
        );
        insertQuery.setString(1, username);
        insertQuery.setString(2, password);
        try {
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1169) {
                throw new UserAlreadyExistsException("User already exists");
            }
            throw e;
        }
        insertQuery.close();

        ResultSet idResult = queryUser(username, password);
        if (idResult.next()) {
            id = idResult.getInt("id");
        } else {
            throw new SQLException("User not found after insertion.");
        }
        idResult.close();
    }

    public static User loginFromDB(String username, String password) throws SQLException, IncorrectCredentialsException {
        ResultSet result = queryUser(username, password);
        if (result.next()) {
            return new User(result.getInt("id"), result.getString("username"), result.getString("password"));
        } else {
            throw new IncorrectCredentialsException("Incorrect credentials");
        }
    }
}
