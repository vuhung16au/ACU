package com.acu.graphql;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    
    @Column(name = "cursor")
    private String cursor;
    
    @Column(name = "genre")
    private String genre; // Keeping for backward compatibility
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "book_genres",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();
    
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Review> reviews = new HashSet<>();
    
    // Default constructor for JPA
    public Book() {}
    
    public Book(String id, String name, int pageCount, String authorId, String genre) {
        this.id = id;
        this.name = name;
        this.pageCount = pageCount;
        this.authorId = authorId;
        this.genre = genre;
        this.cursor = java.util.Base64.getEncoder().encodeToString(id.getBytes());
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
    
    public String getCursor() {
        return cursor;
    }
    
    public void setCursor(String cursor) {
        this.cursor = cursor;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public Set<Genre> getGenres() {
        return genres;
    }
    
    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
    
    public Set<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }
    
    // Helper methods for genre management
    public void addGenre(Genre genre) {
        this.genres.add(genre);
        genre.getBooks().add(this);
    }
    
    public void removeGenre(Genre genre) {
        this.genres.remove(genre);
        genre.getBooks().remove(this);
    }
}
