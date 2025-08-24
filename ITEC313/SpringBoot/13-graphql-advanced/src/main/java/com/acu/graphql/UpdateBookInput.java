package com.acu.graphql;

public class UpdateBookInput {
    private String name;
    private Integer pageCount;
    private String authorId;
    private String genre;
    
    // Default constructor
    public UpdateBookInput() {}
    
    public UpdateBookInput(String name, Integer pageCount, String authorId, String genre) {
        this.name = name;
        this.pageCount = pageCount;
        this.authorId = authorId;
        this.genre = genre;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getPageCount() {
        return pageCount;
    }
    
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }
    
    public String getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
}
