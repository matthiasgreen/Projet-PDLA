package com.projet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.lang.Thread.State;
import java.sql.Connection;

public class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //method to check that a username and password are in the database
    public void checkUsernamePassword(User user){
    }

    //methode to create a new line in the database when the signup button is clicked
    public void toDB(User user){
        Connection connection = DatabaseConnection.getConnection();

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user_pass (username, password) VALUES (?, ?)");
            statement.setString(1, user.username);
            statement.setString(2, user.password);
            int resultset = statement.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error while creating user");

    }
        

    }
    public static User loginFromDB(String username, String password) {
    
        Connection connection = DatabaseConnection.getConnection();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM user_pass";
            ResultSet resultset = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error getting the database");
        }

        //now we build instances of users from the data we got

        //check that it's in the DB
        
        return new User("dev", "dev");

    }
}
