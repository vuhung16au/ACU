package com.acu.car.management.persistence.model;

import java.util.Objects;

/**
 * User entity representing application users for authentication
 */
public class User {
    // User identifier
    Long Id;
    // User's login username
    String username;
    // User's login password
    String password;

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * Constructor with all fields
     */
    public User(Long Id, String username, String password) {
        this.Id = Id;
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor for new users (without ID)
     */
    public User(String username, String password) {
        this(null, username, password);
    }

    // Getters and setters
    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Users are considered equal if they have the same username
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    /**
     * Hash code based on username
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
