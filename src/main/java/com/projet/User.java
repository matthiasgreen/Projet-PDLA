package com.projet;

public class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static User loginFromDB(String username, String password) {
        //TODO
        return new User("dev", "dev");
    }
}
