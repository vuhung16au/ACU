package com.acu.graphql;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    
    @Id
    private String id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "page_count")
    private int pageCount;
    
    @Column(name = "author_id")
    private String authorId;
    
    // Default constructor for JPA
    public Book() {}
    
    public Book(String id, String name, int pageCount, String authorId) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.authorId = authorId;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getPageCount() {
        return pageCount;
    }
    
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
    
    public String getAuthorId() {
        return authorId;
    }
    
    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
