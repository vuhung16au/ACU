package com.acu.hibernate.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

class EntityBasicTest {

    private Author author;
    private Book book;
    private Tag tag;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setName("Test Author");
        author.setEmail("test@example.com");
        author.setBiography("Test biography");
        author.setBirthDate(LocalDate.of(1990, 1, 1));

        book = new Book();
        book.setTitle("Test Book");
        book.setDescription("Test description");
        book.setPublicationYear(2023);
        book.setIsbn("978-1234567890");
        book.setPrice(new BigDecimal("19.99"));
        book.setPageCount(300);

        tag = new Tag();
        tag.setName("Test Tag");
        tag.setDescription("Test tag description");
    }

    @Test
    void testAuthorCreation() {
        // Test adding new data
        assertNotNull(author);
        assertEquals("Test Author", author.getName());
        assertEquals("test@example.com", author.getEmail());
        assertEquals("Test biography", author.getBiography());
        assertEquals(LocalDate.of(1990, 1, 1), author.getBirthDate());
    }

    @Test
    void testBookCreation() {
        // Test adding new data
        assertNotNull(book);
        assertEquals("Test Book", book.getTitle());
        assertEquals("Test description", book.getDescription());
        assertEquals(2023, book.getPublicationYear());
        assertEquals("978-1234567890", book.getIsbn());
        assertEquals(new BigDecimal("19.99"), book.getPrice());
        assertEquals(300, book.getPageCount());
    }

    @Test
    void testTagCreation() {
        // Test adding new data
        assertNotNull(tag);
        assertEquals("Test Tag", tag.getName());
        assertEquals("Test tag description", tag.getDescription());
    }

    @Test
    void testAuthorUpdate() {
        // Test updating data
        author.setName("Updated Author");
        author.setEmail("updated@example.com");
        author.setBiography("Updated biography");

        assertEquals("Updated Author", author.getName());
        assertEquals("updated@example.com", author.getEmail());
        assertEquals("Updated biography", author.getBiography());
    }

    @Test
    void testBookUpdate() {
        // Test updating data
        book.setTitle("Updated Book");
        book.setDescription("Updated description");
        book.setPrice(new BigDecimal("29.99"));

        assertEquals("Updated Book", book.getTitle());
        assertEquals("Updated description", book.getDescription());
        assertEquals(new BigDecimal("29.99"), book.getPrice());
    }

    @Test
    void testTagUpdate() {
        // Test updating data
        tag.setName("Updated Tag");
        tag.setDescription("Updated tag description");

        assertEquals("Updated Tag", tag.getName());
        assertEquals("Updated tag description", tag.getDescription());
    }

    @Test
    void testAuthorBookRelationship() {
        // Test adding book to author
        author.addBook(book);
        
        assertEquals(1, author.getBooks().size());
        assertTrue(author.getBooks().contains(book));
        assertEquals(author, book.getAuthor());
    }

    @Test
    void testBookTagRelationship() {
        // Test adding tag to book
        book.addTag(tag);
        
        assertEquals(1, book.getTags().size());
        assertTrue(book.getTags().contains(tag));
        assertTrue(tag.getBooks().contains(book));
    }

    @Test
    void testRemoveBookFromAuthor() {
        // Test removing data
        author.addBook(book);
        author.removeBook(book);
        
        assertEquals(0, author.getBooks().size());
        assertFalse(author.getBooks().contains(book));
        assertNull(book.getAuthor());
    }

    @Test
    void testRemoveTagFromBook() {
        // Test removing data
        book.addTag(tag);
        book.removeTag(tag);
        
        assertEquals(0, book.getTags().size());
        assertFalse(book.getTags().contains(tag));
        assertFalse(tag.getBooks().contains(book));
    }

    @Test
    void testAuthorConstructor() {
        // Test constructor with parameters
        Author newAuthor = new Author("Constructor Author", "constructor@example.com", 
                                    "Constructor biography", LocalDate.of(1985, 5, 15));
        
        assertEquals("Constructor Author", newAuthor.getName());
        assertEquals("constructor@example.com", newAuthor.getEmail());
        assertEquals("Constructor biography", newAuthor.getBiography());
        assertEquals(LocalDate.of(1985, 5, 15), newAuthor.getBirthDate());
    }

    @Test
    void testBookConstructor() {
        // Test constructor with parameters
        Book newBook = new Book("Constructor Book", "Constructor description", 
                               2020, "978-0987654321", new BigDecimal("15.99"), 250);
        
        assertEquals("Constructor Book", newBook.getTitle());
        assertEquals("Constructor description", newBook.getDescription());
        assertEquals(2020, newBook.getPublicationYear());
        assertEquals("978-0987654321", newBook.getIsbn());
        assertEquals(new BigDecimal("15.99"), newBook.getPrice());
        assertEquals(250, newBook.getPageCount());
    }

    @Test
    void testTagConstructor() {
        // Test constructor with parameters
        Tag newTag = new Tag("Constructor Tag", "Constructor tag description");
        
        assertEquals("Constructor Tag", newTag.getName());
        assertEquals("Constructor tag description", newTag.getDescription());
    }

    @Test
    void testEntityLifecycle() {
        // Test that entities can be created, updated, and have relationships managed
        // without requiring a database connection
        
        // Create entities
        Author author1 = new Author("Author 1", "author1@example.com", "Bio 1", LocalDate.of(1980, 1, 1));
        Book book1 = new Book("Book 1", "Description 1", 2020, "978-1", new BigDecimal("19.99"), 200);
        Tag tag1 = new Tag("Tag 1", "Description 1");
        
        // Test adding data - establish relationships
        author1.addBook(book1);
        book1.addTag(tag1);
        
        // Verify relationships
        assertEquals(1, author1.getBooks().size());
        assertEquals(1, book1.getTags().size());
        assertEquals(1, tag1.getBooks().size());
        
        // Test updating data
        author1.setName("Updated Author 1");
        book1.setTitle("Updated Book 1");
        tag1.setName("Updated Tag 1");
        
        assertEquals("Updated Author 1", author1.getName());
        assertEquals("Updated Book 1", book1.getTitle());
        assertEquals("Updated Tag 1", tag1.getName());
        
        // Test deleting data - remove relationships
        author1.removeBook(book1);
        book1.removeTag(tag1);
        
        assertEquals(0, author1.getBooks().size());
        assertEquals(0, book1.getTags().size());
        assertEquals(0, tag1.getBooks().size());
    }
}
