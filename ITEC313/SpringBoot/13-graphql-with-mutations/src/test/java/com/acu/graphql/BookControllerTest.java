package com.acu.graphql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import static org.mockito.Mockito.when;

@GraphQlTest(BookController.class)
class BookControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;
    
    @MockBean
    private BookRepository bookRepository;
    
    @MockBean
    private AuthorRepository authorRepository;
    
    @BeforeEach
    void setUp() {
        // Setup test data
        Author author1 = new Author("author-1", "Donald", "Horne");
        Author author2 = new Author("author-2", "Norman", "Lindsay");
        
        Book book1 = new Book("book-1", "The Lucky Country", 300, "author-1");
        Book book2 = new Book("book-2", "The Magic Pudding: Being The Adventures of Bunyip Bluegum and his friends Bill Barnacle and Sam Sawnoff", 250, "author-2");
        
        // Mock repository responses
        when(bookRepository.findById("book-1")).thenReturn(java.util.Optional.of(book1));
        when(bookRepository.findById("book-2")).thenReturn(java.util.Optional.of(book2));
        when(bookRepository.findById("non-existent")).thenReturn(java.util.Optional.empty());
        
        when(authorRepository.findById("author-1")).thenReturn(java.util.Optional.of(author1));
        when(authorRepository.findById("author-2")).thenReturn(java.util.Optional.of(author2));
    }

    @Test
    void shouldReturnBookWithAuthor() {
        String document = """
                query {
                    bookById(id: "book-1") {
                        id
                        name
                        pageCount
                        author {
                            id
                            firstName
                            lastName
                        }
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.id")
                .entity(String.class)
                .isEqualTo("book-1");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.name")
                .entity(String.class)
                .isEqualTo("The Lucky Country");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.author.firstName")
                .entity(String.class)
                .isEqualTo("Donald");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.author.lastName")
                .entity(String.class)
                .isEqualTo("Horne");
    }

    @Test
    void shouldReturnSecondBookWithAuthor() {
        String document = """
                query {
                    bookById(id: "book-2") {
                        id
                        name
                        pageCount
                        author {
                            id
                            firstName
                            lastName
                        }
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.id")
                .entity(String.class)
                .isEqualTo("book-2");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.name")
                .entity(String.class)
                .isEqualTo("The Magic Pudding: Being The Adventures of Bunyip Bluegum and his friends Bill Barnacle and Sam Sawnoff");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.author.firstName")
                .entity(String.class)
                .isEqualTo("Norman");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.author.lastName")
                .entity(String.class)
                .isEqualTo("Lindsay");
    }

    @Test
    void shouldReturnNullForNonExistentBook() {
        String document = """
                query {
                    bookById(id: "non-existent") {
                        id
                        name
                        pageCount
                        author {
                            id
                            firstName
                            lastName
                        }
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.bookById")
                .valueIsNull();
    }
}
