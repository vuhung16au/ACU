package com.acu.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private AuthorRepository authorRepository;
    
    @QueryMapping
    public Book bookById(@Argument String id) {
        return bookRepository.findById(id).orElse(null);
    }
    
    @QueryMapping
    public BookConnection books(@Argument Integer first, @Argument String after, @Argument String search, @Argument String genre) {
        // Default to 10 if first is not specified
        int limit = (first != null) ? Math.min(first, 100) : 10; // Max 100 items per page
        
        Page<Book> bookPage;
        if (after != null && !after.isEmpty()) {
            // Find books after the cursor with filters
            if (search != null && !search.isEmpty() && genre != null && !genre.isEmpty()) {
                bookPage = bookRepository.findBooksAfterCursorWithSearchAndGenre(after, search, genre, PageRequest.of(0, limit + 1));
            } else if (search != null && !search.isEmpty()) {
                bookPage = bookRepository.findBooksAfterCursorWithSearch(after, search, PageRequest.of(0, limit + 1));
            } else if (genre != null && !genre.isEmpty()) {
                bookPage = bookRepository.findBooksAfterCursorWithGenre(after, genre, PageRequest.of(0, limit + 1));
            } else {
                bookPage = bookRepository.findBooksAfterCursor(after, PageRequest.of(0, limit + 1));
            }
        } else {
            // First page with filters
            if (search != null && !search.isEmpty() && genre != null && !genre.isEmpty()) {
                bookPage = bookRepository.findBooksWithSearchAndGenre(search, genre, PageRequest.of(0, limit + 1));
            } else if (search != null && !search.isEmpty()) {
                bookPage = bookRepository.findBooksWithSearch(search, PageRequest.of(0, limit + 1));
            } else if (genre != null && !genre.isEmpty()) {
                bookPage = bookRepository.findBooksWithGenre(genre, PageRequest.of(0, limit + 1));
            } else {
                bookPage = bookRepository.findBooks(PageRequest.of(0, limit + 1));
            }
        }
        
        List<Book> books = bookPage.getContent();
        boolean hasNextPage = books.size() > limit;
        
        // Remove the extra item we fetched to check if there's a next page
        if (hasNextPage) {
            books = books.subList(0, limit);
        }
        
        // Convert books to edges
        List<BookEdge> edges = books.stream()
                .map(book -> new BookEdge(book.getCursor(), book))
                .collect(Collectors.toList());
        
        // Create page info
        String startCursor = edges.isEmpty() ? null : edges.get(0).getCursor();
        String endCursor = edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor();
        PageInfo pageInfo = new PageInfo(hasNextPage, after != null && !after.isEmpty(), startCursor, endCursor);
        
        return new BookConnection(edges, pageInfo, (int) bookRepository.count());
    }

    @SchemaMapping
    public Author author(Book book) {
        return authorRepository.findById(book.getAuthorId()).orElse(null);
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Book createBook(@Argument CreateBookInput input) {
        String id = "book-" + UUID.randomUUID().toString().substring(0, 8);
        Book book = new Book(id, input.getName(), input.getPageCount(), input.getAuthorId(), input.getGenre());
        return bookRepository.save(book);
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Author createAuthor(@Argument CreateAuthorInput input) {
        String id = "author-" + UUID.randomUUID().toString().substring(0, 8);
        Author author = new Author(id, input.getFirstName(), input.getLastName());
        return authorRepository.save(author);
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Book updateBook(@Argument String id, @Argument UpdateBookInput input) {
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook == null) {
            return null;
        }
        
        if (input.getName() != null) {
            existingBook.setName(input.getName());
        }
        if (input.getPageCount() != null) {
            existingBook.setPageCount(input.getPageCount());
        }
        if (input.getAuthorId() != null) {
            existingBook.setAuthorId(input.getAuthorId());
        }
        if (input.getGenre() != null) {
            existingBook.setGenre(input.getGenre());
        }
        
        return bookRepository.save(existingBook);
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteBook(@Argument String id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
