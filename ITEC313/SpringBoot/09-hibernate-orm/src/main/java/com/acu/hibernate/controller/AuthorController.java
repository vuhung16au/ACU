package com.acu.hibernate.controller;

import com.acu.hibernate.entity.Author;
import com.acu.hibernate.entity.Book;
import com.acu.hibernate.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // CRUD operations
    @GetMapping
    public ResponseEntity<Page<Author>> getAllAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
            Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<Author> authors = authorService.getAllAuthorsWithPagination(pageable);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id);
        return author.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/with-books")
    public ResponseEntity<Author> getAuthorByIdWithBooks(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorByIdWithBooks(id);
        return author.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        Author createdAuthor = authorService.createAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author author) {
        try {
            Author updatedAuthor = authorService.updateAuthor(id, author);
            return ResponseEntity.ok(updatedAuthor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Search operations
    @GetMapping("/search")
    public ResponseEntity<List<Author>> searchAuthors(@RequestParam String keyword) {
        List<Author> authors = authorService.searchAuthorsByKeyword(keyword);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/search/name")
    public ResponseEntity<Page<Author>> searchAuthorsByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authors = authorService.findAuthorsByNameWithPagination(name, pageable);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/search/birth-date")
    public ResponseEntity<List<Author>> searchAuthorsByBirthDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Author> authors = authorService.findAuthorsByBirthDateRange(start, end);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/with-books")
    public ResponseEntity<List<Author>> getAuthorsWithBooks() {
        List<Author> authors = authorService.findAuthorsWithBooks();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/with-books/eager")
    public ResponseEntity<List<Author>> getAllAuthorsWithBooksEager() {
        List<Author> authors = authorService.findAllAuthorsWithBooks();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/min-books/{minBooks}")
    public ResponseEntity<List<Author>> getAuthorsWithAtLeastBooks(@PathVariable int minBooks) {
        List<Author> authors = authorService.findAuthorsWithAtLeastBooks(minBooks);
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/born-after")
    public ResponseEntity<List<Author>> getAuthorsBornAfter(@RequestParam String date) {
        LocalDate birthDate = LocalDate.parse(date);
        List<Author> authors = authorService.findAuthorsBornAfter(birthDate);
        return ResponseEntity.ok(authors);
    }

    // Statistics
    @GetMapping("/stats/more-than-books")
    public ResponseEntity<Long> countAuthorsWithMoreThanBooks(@RequestParam int minBooks) {
        long count = authorService.countAuthorsWithMoreThanBooks(minBooks);
        return ResponseEntity.ok(count);
    }

    // Book-related operations
    @GetMapping("/{id}/books")
    public ResponseEntity<List<Book>> getAuthorBooks(@PathVariable Long id) {
        try {
            List<Book> books = authorService.getAuthorBooks(id);
            return ResponseEntity.ok(books);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/books")
    public ResponseEntity<Book> addBookToAuthor(@PathVariable Long id, @Valid @RequestBody Book book) {
        try {
            Book addedBook = authorService.addBookToAuthor(id, book);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{authorId}/books/{bookId}")
    public ResponseEntity<Void> removeBookFromAuthor(@PathVariable Long authorId, @PathVariable Long bookId) {
        try {
            authorService.removeBookFromAuthor(authorId, bookId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
