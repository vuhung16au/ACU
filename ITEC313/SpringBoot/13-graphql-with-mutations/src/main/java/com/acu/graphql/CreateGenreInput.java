package com.acu.graphql;

public class CreateGenreInput {
    private String name;
    private String description;
    
    // Default constructor
    public CreateGenreInput() {}
    
    public CreateGenreInput(String name, String description) {
        this.name = name;
        this.description = description;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
