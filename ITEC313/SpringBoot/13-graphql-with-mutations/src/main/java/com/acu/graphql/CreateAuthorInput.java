package com.acu.graphql;

public class CreateAuthorInput {
    private String firstName;
    private String lastName;
    
    // Default constructor
    public CreateAuthorInput() {}
    
    public CreateAuthorInput(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
