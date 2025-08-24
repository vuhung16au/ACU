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
        author.setName("Donald Horne");
        author.setEmail("donald.horne@example.com");
        author.setBiography("Australian journalist, writer, social critic and academic");
        author.setBirthDate(LocalDate.of(1921, 12, 26));

        book = new Book();
        book.setTitle("The Lucky Country");
        book.setDescription("A 1964 book about Australia that has become a nickname for the country. The title is generally used favourably, although the origin of the phrase was negative in the context of the book.");
        book.setPublicationYear(1964);
        book.setIsbn("978-0143180029");
        book.setPrice(new BigDecimal("22.99"));
        book.setPageCount(288);

        tag = new Tag();
        tag.setName("Non-fiction");
        tag.setDescription("Non-fiction literature and academic works");
    }

    @Test
    void testAuthorCreation() {
        // Test adding new data
        assertNotNull(author);
        assertEquals("Donald Horne", author.getName());
        assertEquals("donald.horne@example.com", author.getEmail());
        assertEquals("Australian journalist, writer, social critic and academic", author.getBiography());
        assertEquals(LocalDate.of(1921, 12, 26), author.getBirthDate());
    }

    @Test
    void testBookCreation() {
        // Test adding new data
        assertNotNull(book);
        assertEquals("The Lucky Country", book.getTitle());
        assertEquals("A 1964 book about Australia that has become a nickname for the country. The title is generally used favourably, although the origin of the phrase was negative in the context of the book.", book.getDescription());
        assertEquals(1964, book.getPublicationYear());
        assertEquals("978-0143180029", book.getIsbn());
        assertEquals(new BigDecimal("22.99"), book.getPrice());
        assertEquals(288, book.getPageCount());
    }

    @Test
    void testTagCreation() {
        // Test adding new data
        assertNotNull(tag);
        assertEquals("Non-fiction", tag.getName());
        assertEquals("Non-fiction literature and academic works", tag.getDescription());
    }

    @Test
    void testAuthorUpdate() {
        // Test updating data
        author.setName("Updated Donald Horne");
        author.setEmail("updated.donald.horne@example.com");
        author.setBiography("Updated Australian journalist biography");

        assertEquals("Updated Donald Horne", author.getName());
        assertEquals("updated.donald.horne@example.com", author.getEmail());
        assertEquals("Updated Australian journalist biography", author.getBiography());
    }

    @Test
    void testBookUpdate() {
        // Test updating data
        book.setTitle("Updated Lucky Country");
        book.setDescription("Updated description about Australia");
        book.setPrice(new BigDecimal("24.99"));

        assertEquals("Updated Lucky Country", book.getTitle());
        assertEquals("Updated description about Australia", book.getDescription());
        assertEquals(new BigDecimal("24.99"), book.getPrice());
    }

    @Test
    void testTagUpdate() {
        // Test updating data
        tag.setName("Updated Non-fiction");
        tag.setDescription("Updated non-fiction literature description");

        assertEquals("Updated Non-fiction", tag.getName());
        assertEquals("Updated non-fiction literature description", tag.getDescription());
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
        Author newAuthor = new Author("Donald Horne", "donald.horne@example.com", 
                                    "Australian journalist, writer, social critic and academic", LocalDate.of(1921, 12, 26));
        
        assertEquals("Donald Horne", newAuthor.getName());
        assertEquals("donald.horne@example.com", newAuthor.getEmail());
        assertEquals("Australian journalist, writer, social critic and academic", newAuthor.getBiography());
        assertEquals(LocalDate.of(1921, 12, 26), newAuthor.getBirthDate());
    }

    @Test
    void testBookConstructor() {
        // Test constructor with parameters
        Book newBook = new Book("The Lucky Country", "A 1964 book about Australia", 
                               1964, "978-0143180029", new BigDecimal("22.99"), 288);
        
        assertEquals("The Lucky Country", newBook.getTitle());
        assertEquals("A 1964 book about Australia", newBook.getDescription());
        assertEquals(1964, newBook.getPublicationYear());
        assertEquals("978-0143180029", newBook.getIsbn());
        assertEquals(new BigDecimal("22.99"), newBook.getPrice());
        assertEquals(288, newBook.getPageCount());
    }

    @Test
    void testTagConstructor() {
        // Test constructor with parameters
        Tag newTag = new Tag("Non-fiction", "Non-fiction literature and academic works");
        
        assertEquals("Non-fiction", newTag.getName());
        assertEquals("Non-fiction literature and academic works", newTag.getDescription());
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
