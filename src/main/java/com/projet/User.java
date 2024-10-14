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
            //this checks if the username and password are in the database without
            String query = "SELECT * FROM user_pass WHERE username = '" + username + "' AND password = '" + password + "';";
            ResultSet resultset = statement.executeQuery(query);
            if (resultset !=null){
                //the user and pass is in the DB, procceed to the next page
                return new User(username, password);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error getting the database");
        }
        
        return new User("dev", "dev");

    }
}
