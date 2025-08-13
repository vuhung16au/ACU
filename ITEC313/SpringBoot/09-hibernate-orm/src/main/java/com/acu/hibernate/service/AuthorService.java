package com.acu.hibernate.service;

import com.acu.hibernate.entity.Author;
import com.acu.hibernate.entity.Book;
import com.acu.hibernate.repository.AuthorRepository;
import com.acu.hibernate.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    // CRUD operations
    public List<Author> getAllAuthors() {
        logger.info("Fetching all authors");
        return authorRepository.findAll();
    }

    public Page<Author> getAllAuthorsWithPagination(Pageable pageable) {
        logger.info("Fetching authors with pagination: {}", pageable);
        return authorRepository.findAll(pageable);
    }

    public Optional<Author> getAuthorById(Long id) {
        logger.info("Fetching author with id: {}", id);
        return authorRepository.findById(id);
    }

    public Optional<Author> getAuthorByIdWithBooks(Long id) {
        logger.info("Fetching author with books for id: {}", id);
        return authorRepository.findByIdWithBooks(id);
    }

    public Author createAuthor(Author author) {
        logger.info("Creating new author: {}", author.getName());
        
        // Check if email already exists
        if (author.getEmail() != null && authorRepository.findByEmail(author.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Author with email " + author.getEmail() + " already exists");
        }
        
        return authorRepository.save(author);
    }

    public Author updateAuthor(Long id, Author authorDetails) {
        logger.info("Updating author with id: {}", id);
        
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        
        // Check if email is being changed and if it already exists
        if (authorDetails.getEmail() != null && !authorDetails.getEmail().equals(author.getEmail())) {
            if (authorRepository.findByEmail(authorDetails.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Author with email " + authorDetails.getEmail() + " already exists");
            }
        }
        
        author.setName(authorDetails.getName());
        author.setEmail(authorDetails.getEmail());
        author.setBiography(authorDetails.getBiography());
        author.setBirthDate(authorDetails.getBirthDate());
        
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        logger.info("Deleting author with id: {}", id);
        
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + id));
        
        // Check if author has books
        if (!author.getBooks().isEmpty()) {
            throw new IllegalStateException("Cannot delete author with existing books. Delete books first.");
        }
        
        authorRepository.delete(author);
    }

    // Search and query operations
    public List<Author> searchAuthorsByKeyword(String keyword) {
        logger.info("Searching authors with keyword: {}", keyword);
        return authorRepository.searchByKeyword(keyword);
    }

    public List<Author> findAuthorsByName(String name) {
        logger.info("Finding authors by name: {}", name);
        return authorRepository.findByNameContainingIgnoreCase(name);
    }

    public Page<Author> findAuthorsByNameWithPagination(String name, Pageable pageable) {
        logger.info("Finding authors by name with pagination: {}", name);
        return authorRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public List<Author> findAuthorsByBirthDateRange(LocalDate startDate, LocalDate endDate) {
        logger.info("Finding authors by birth date range: {} to {}", startDate, endDate);
        return authorRepository.findByBirthDateBetween(startDate, endDate);
    }

    public List<Author> findAuthorsWithBooks() {
        logger.info("Finding authors with books");
        return authorRepository.findByBooksIsNotEmpty();
    }

    public List<Author> findAuthorsWithAtLeastBooks(int minBooks) {
        logger.info("Finding authors with at least {} books", minBooks);
        return authorRepository.findAuthorsWithAtLeastBooks(minBooks);
    }

    public List<Author> findAuthorsBornAfter(LocalDate date) {
        logger.info("Finding authors born after: {}", date);
        return authorRepository.findAuthorsBornAfter(date);
    }

    public List<Author> findAllAuthorsWithBooks() {
        logger.info("Finding all authors with books (eager loading)");
        return authorRepository.findAllWithBooks();
    }

    // Statistics
    public long countAuthorsWithMoreThanBooks(int minBooks) {
        logger.info("Counting authors with more than {} books", minBooks);
        return authorRepository.countAuthorsWithMoreThanBooks(minBooks);
    }

    // Book-related operations
    public List<Book> getAuthorBooks(Long authorId) {
        logger.info("Fetching books for author with id: {}", authorId);
        
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + authorId));
        
        return author.getBooks();
    }

    public Book addBookToAuthor(Long authorId, Book book) {
        logger.info("Adding book to author with id: {}", authorId);
        
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + authorId));
        
        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);
        author.addBook(savedBook);
        
        return savedBook;
    }

    public void removeBookFromAuthor(Long authorId, Long bookId) {
        logger.info("Removing book {} from author {}", bookId, authorId);
        
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new EntityNotFoundException("Author not found with id: " + authorId));
        
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        
        if (!book.getAuthor().getId().equals(authorId)) {
            throw new IllegalArgumentException("Book does not belong to the specified author");
        }
        
        author.removeBook(book);
        bookRepository.delete(book);
    }
}
