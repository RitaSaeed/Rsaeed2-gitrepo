package com.example.cookingovereasy;

/**
 * Class containing user information.
 */
public class Users {

    private String username;

    public Users() {
        // empty constructor
        // required for firebase
    }

    // Constructor with variables
    public Users(String username) {
        this.username = username;
    }

    // getter methods for variables
    public String getUsername() {
        return username;
    }

    // setter methods for variables
    public void setUsername(String userName) {
        this.username = username;
    }
}
