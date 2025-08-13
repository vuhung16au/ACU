package com.acu.hibernate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false)
    private String name;
    
    @Email(message = "Please provide a valid email address")
    @Column(unique = true)
    private String email;
    
    @Size(max = 500, message = "Biography must not exceed 500 characters")
    @Column(length = 500)
    private String biography;
    
    @Column(name = "birth_date")
    private LocalDate birthDate;
    
    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;
    
    @Column(name = "updated_at")
    private LocalDate updatedAt;
    
    // One-to-Many relationship with Book
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDate.now();
        updatedAt = LocalDate.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDate.now();
    }
    
    // Default constructor
    public Author() {}
    
    // Constructor with parameters
    public Author(String name, String email, String biography, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.biography = biography;
        this.birthDate = birthDate;
    }
    
    // Helper methods for managing relationships
    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }
    
    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public String getBiography() {
        return biography;
    }
    
    public void setBiography(String biography) {
        this.biography = biography;
    }
    
    public LocalDate getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<Book> getBooks() {
        return books;
    }
    
    public void setBooks(List<Book> books) {
        this.books = books;
    }
    
    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", biography='" + biography + '\'' +
                ", birthDate=" + birthDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", booksCount=" + (books != null ? books.size() : 0) +
                '}';
    }
}
