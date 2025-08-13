package com.acu.webapp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * User model class for form handling and validation
 * 
 * This class demonstrates:
 * - Bean validation annotations
 * - Form data binding
 * - Input validation with custom messages
 */
public class User {

    @NotBlank(message = "{user.name.notBlank}")
    @Size(min = 2, max = 50, message = "{user.name.size}")
    private String name;

    @NotBlank(message = "{user.email.notBlank}")
    @Email(message = "{user.email.valid}")
    private String email;

    @NotBlank(message = "{user.message.notBlank}")
    @Size(min = 10, max = 500, message = "{user.message.size}")
    private String message;

    private String language;

    // Constructors
    public User() {}

    public User(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
