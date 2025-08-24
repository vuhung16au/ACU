package com.acu.graphql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

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

    @SchemaMapping
    public Author author(Book book) {
        return authorRepository.findById(book.getAuthorId()).orElse(null);
    }
    
    @MutationMapping
    public Book createBook(@Argument CreateBookInput input) {
        String id = "book-" + UUID.randomUUID().toString().substring(0, 8);
        Book book = new Book(id, input.getName(), input.getPageCount(), input.getAuthorId());
        return bookRepository.save(book);
    }
    
    @MutationMapping
    public Author createAuthor(@Argument CreateAuthorInput input) {
        String id = "author-" + UUID.randomUUID().toString().substring(0, 8);
        Author author = new Author(id, input.getFirstName(), input.getLastName());
        return authorRepository.save(author);
    }
    
    @MutationMapping
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
        
        return bookRepository.save(existingBook);
    }
    
    @MutationMapping
    public Boolean deleteBook(@Argument String id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
