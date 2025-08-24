package com.acu.graphql;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import java.util.Arrays;
import java.util.List;

@GraphQlTest(BookController.class)
class BookControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;
    
    @MockBean
    private BookRepository bookRepository;
    
    @MockBean
    private AuthorRepository authorRepository;
    
    @MockBean
    private GenreRepository genreRepository;
    
    @BeforeEach
    void setUp() {
        // Setup test data
        Author author1 = new Author("author-1", "Donald", "Horne");
        Author author2 = new Author("author-2", "Norman", "Lindsay");
        
        Book book1 = new Book("book-1", "The Lucky Country", 300, "author-1", "Non-Fiction");
        Book book2 = new Book("book-2", "The Magic Pudding: Being The Adventures of Bunyip Bluegum and his friends Bill Barnacle and Sam Sawnoff", 250, "author-2", "Children");
        
        // Mock repository responses
        when(bookRepository.findById("book-1")).thenReturn(java.util.Optional.of(book1));
        when(bookRepository.findById("book-2")).thenReturn(java.util.Optional.of(book2));
        when(bookRepository.findById("non-existent")).thenReturn(java.util.Optional.empty());
        
        when(authorRepository.findById("author-1")).thenReturn(java.util.Optional.of(author1));
        when(authorRepository.findById("author-2")).thenReturn(java.util.Optional.of(author2));
        
        // Mock save operations
        when(bookRepository.save(any(Book.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(authorRepository.save(any(Author.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // Mock existsById
        when(bookRepository.existsById("book-1")).thenReturn(true);
        when(bookRepository.existsById("non-existent")).thenReturn(false);
        
        // Mock pagination methods
        List<Book> allBooks = Arrays.asList(book1, book2);
        Page<Book> bookPage = new PageImpl<>(allBooks, PageRequest.of(0, 10), allBooks.size());
        when(bookRepository.findBooks(any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursor(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksWithSearch(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksWithGenre(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksWithSearchAndGenre(anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        
        // Mock sorting methods
        when(bookRepository.findBooksOrderByName(any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorOrderByName(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksOrderByPageCount(any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorOrderByPageCount(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksOrderByGenre(any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorOrderByGenre(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        
        // Mock search with sorting
        when(bookRepository.findBooksWithSearchOrderByName(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorWithSearchOrderByName(anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksWithSearchOrderByPageCount(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorWithSearchOrderByPageCount(anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksWithSearchOrderByGenre(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorWithSearchOrderByGenre(anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        
        // Mock genre with sorting
        when(bookRepository.findBooksWithGenreOrderByName(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorWithGenreOrderByName(anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksWithGenreOrderByPageCount(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorWithGenreOrderByPageCount(anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksWithGenreOrderByGenre(anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorWithGenreOrderByGenre(anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        
        // Mock search and genre with sorting
        when(bookRepository.findBooksWithSearchAndGenreOrderByName(anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorWithSearchAndGenreOrderByName(anyString(), anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksWithSearchAndGenreOrderByPageCount(anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorWithSearchAndGenreOrderByPageCount(anyString(), anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksWithSearchAndGenreOrderByGenre(anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        when(bookRepository.findBooksAfterCursorWithSearchAndGenreOrderByGenre(anyString(), anyString(), anyString(), any(PageRequest.class))).thenReturn(bookPage);
        
        when(bookRepository.count()).thenReturn(2L);
        
        // Mock genre repository methods
        when(genreRepository.findAll()).thenReturn(Arrays.asList());
        when(genreRepository.findById(anyString())).thenReturn(java.util.Optional.empty());
        when(genreRepository.findByBookId(anyString())).thenReturn(Arrays.asList());
        when(genreRepository.save(any(Genre.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(genreRepository.existsById(anyString())).thenReturn(false);
    }

    @Test
    @WithMockUser(roles = "USER")
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
    @WithMockUser(roles = "USER")
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
    @WithMockUser(roles = "USER")
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
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateBook() {
        String document = """
                mutation {
                    createBook(input: {
                        name: "New Book"
                        pageCount: 200
                        authorId: "author-1"
                        genre: "Fiction"
                    }) {
                        id
                        name
                        pageCount
                        genre
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
                .path("data.createBook.name")
                .entity(String.class)
                .isEqualTo("New Book");

        graphQlTester.document(document)
                .execute()
                .path("data.createBook.pageCount")
                .entity(Integer.class)
                .isEqualTo(200);

        graphQlTester.document(document)
                .execute()
                .path("data.createBook.author.firstName")
                .entity(String.class)
                .isEqualTo("Donald");
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateAuthor() {
        String document = """
                mutation {
                    createAuthor(input: {
                        firstName: "John"
                        lastName: "Doe"
                    }) {
                        id
                        firstName
                        lastName
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.createAuthor.firstName")
                .entity(String.class)
                .isEqualTo("John");

        graphQlTester.document(document)
                .execute()
                .path("data.createAuthor.lastName")
                .entity(String.class)
                .isEqualTo("Doe");
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldUpdateBook() {
        String document = """
                mutation {
                    updateBook(id: "book-1", input: {
                        name: "Updated Book Name"
                        pageCount: 350
                    }) {
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
                .path("data.updateBook.name")
                .entity(String.class)
                .isEqualTo("Updated Book Name");

        graphQlTester.document(document)
                .execute()
                .path("data.updateBook.pageCount")
                .entity(Integer.class)
                .isEqualTo(350);
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldDeleteBook() {
        String document = """
                mutation {
                    deleteBook(id: "book-1")
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.deleteBook")
                .entity(Boolean.class)
                .isEqualTo(true);
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldReturnFalseForDeleteNonExistentBook() {
        String document = """
                mutation {
                    deleteBook(id: "non-existent")
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.deleteBook")
                .entity(Boolean.class)
                .isEqualTo(false);
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBooksWithPagination() {
        String document = """
                query {
                    books(first: 2) {
                        edges {
                            cursor
                            node {
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
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                            startCursor
                            endCursor
                        }
                        totalCount
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(2);

        graphQlTester.document(document)
                .execute()
                .path("data.books.pageInfo.hasNextPage")
                .entity(Boolean.class)
                .isEqualTo(false);

        graphQlTester.document(document)
                .execute()
                .path("data.books.pageInfo.hasPreviousPage")
                .entity(Boolean.class)
                .isEqualTo(false);

        graphQlTester.document(document)
                .execute()
                .path("data.books.totalCount")
                .entity(Integer.class)
                .isEqualTo(2);
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBooksWithCursorPagination() {
        String document = """
                query {
                    books(first: 1, after: "Ym9vay0x") {
                        edges {
                            cursor
                            node {
                                id
                                name
                                pageCount
                            }
                        }
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                        }
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(1);

        graphQlTester.document(document)
                .execute()
                .path("data.books.pageInfo.hasPreviousPage")
                .entity(Boolean.class)
                .isEqualTo(true);
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBooksWithSearchFilter() {
        String document = """
                query {
                    books(first: 2, search: "Lucky") {
                        edges {
                            cursor
                            node {
                                id
                                name
                                pageCount
                                genre
                                author {
                                    id
                                    firstName
                                    lastName
                                }
                            }
                        }
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                            startCursor
                            endCursor
                        }
                        totalCount
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(2);

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges[0].node.genre")
                .entity(String.class)
                .isEqualTo("Non-Fiction");
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBooksWithGenreFilter() {
        String document = """
                query {
                    books(first: 2, genre: "Non-Fiction") {
                        edges {
                            cursor
                            node {
                                id
                                name
                                pageCount
                                genre
                                author {
                                    id
                                    firstName
                                    lastName
                                }
                            }
                        }
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                            startCursor
                            endCursor
                        }
                        totalCount
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(2);

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges[0].node.genre")
                .entity(String.class)
                .isEqualTo("Non-Fiction");
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBooksWithSearchAndGenreFilter() {
        String document = """
                query {
                    books(first: 2, search: "Lucky", genre: "Non-Fiction") {
                        edges {
                            cursor
                            node {
                                id
                                name
                                pageCount
                                genre
                                author {
                                    id
                                    firstName
                                    lastName
                                }
                            }
                        }
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                            startCursor
                            endCursor
                        }
                        totalCount
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(2);

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges[0].node.genre")
                .entity(String.class)
                .isEqualTo("Non-Fiction");
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBookWithGenre() {
        String document = """
                query {
                    bookById(id: "book-1") {
                        id
                        name
                        pageCount
                        genre
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
                .path("data.bookById.genre")
                .entity(String.class)
                .isEqualTo("Non-Fiction");
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBooksOrderedByName() {
        String document = """
                query {
                    books(first: 2, orderBy: NAME) {
                        edges {
                            cursor
                            node {
                                id
                                name
                                pageCount
                                genre
                                author {
                                    id
                                    firstName
                                    lastName
                                }
                            }
                        }
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                            startCursor
                            endCursor
                        }
                        totalCount
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(2);

        graphQlTester.document(document)
                .execute()
                .path("data.books.totalCount")
                .entity(Integer.class)
                .isEqualTo(2);
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBooksOrderedByPageCount() {
        String document = """
                query {
                    books(first: 2, orderBy: PAGE_COUNT) {
                        edges {
                            cursor
                            node {
                                id
                                name
                                pageCount
                                genre
                                author {
                                    id
                                    firstName
                                    lastName
                                }
                            }
                        }
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                            startCursor
                            endCursor
                        }
                        totalCount
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(2);

        graphQlTester.document(document)
                .execute()
                .path("data.books.totalCount")
                .entity(Integer.class)
                .isEqualTo(2);
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBooksOrderedByGenre() {
        String document = """
                query {
                    books(first: 2, orderBy: GENRE) {
                        edges {
                            cursor
                            node {
                                id
                                name
                                pageCount
                                genre
                                author {
                                    id
                                    firstName
                                    lastName
                                }
                            }
                        }
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                            startCursor
                            endCursor
                        }
                        totalCount
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(2);

        graphQlTester.document(document)
                .execute()
                .path("data.books.totalCount")
                .entity(Integer.class)
                .isEqualTo(2);
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBooksWithSearchAndOrderBy() {
        String document = """
                query {
                    books(first: 2, search: "Lucky", orderBy: NAME) {
                        edges {
                            cursor
                            node {
                                id
                                name
                                pageCount
                                genre
                                author {
                                    id
                                    firstName
                                    lastName
                                }
                            }
                        }
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                            startCursor
                            endCursor
                        }
                        totalCount
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(2);

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges[0].node.genre")
                .entity(String.class)
                .isEqualTo("Non-Fiction");
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBooksWithGenreAndOrderBy() {
        String document = """
                query {
                    books(first: 2, genre: "Non-Fiction", orderBy: PAGE_COUNT) {
                        edges {
                            cursor
                            node {
                                id
                                name
                                pageCount
                                genre
                                author {
                                    id
                                    firstName
                                    lastName
                                }
                            }
                        }
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                            startCursor
                            endCursor
                        }
                        totalCount
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(2);

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges[0].node.genre")
                .entity(String.class)
                .isEqualTo("Non-Fiction");
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldReturnBooksWithSearchGenreAndOrderBy() {
        String document = """
                query {
                    books(first: 2, search: "Lucky", genre: "Non-Fiction", orderBy: GENRE) {
                        edges {
                            cursor
                            node {
                                id
                                name
                                pageCount
                                genre
                                author {
                                    id
                                    firstName
                                    lastName
                                }
                            }
                        }
                        pageInfo {
                            hasNextPage
                            hasPreviousPage
                            startCursor
                            endCursor
                        }
                        totalCount
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(2);

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges[0].node.genre")
                .entity(String.class)
                .isEqualTo("Non-Fiction");
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldDemonstrateFieldSelection() {
        // Test 1: Query with only name and genre fields
        String document1 = """
                query {
                    bookById(id: "book-1") {
                        name
                        genre
                    }
                }
                """;

        graphQlTester.document(document1)
                .execute()
                .path("data.bookById.name")
                .entity(String.class)
                .isEqualTo("The Lucky Country");

        graphQlTester.document(document1)
                .execute()
                .path("data.bookById.genre")
                .entity(String.class)
                .isEqualTo("Non-Fiction");

        // Test 2: Query with name, genre, and author fields (nested selection)
        String document2 = """
                query {
                    bookById(id: "book-1") {
                        name
                        genre
                        author {
                            firstName
                            lastName
                        }
                    }
                }
                """;

        graphQlTester.document(document2)
                .execute()
                .path("data.bookById.name")
                .entity(String.class)
                .isEqualTo("The Lucky Country");

        graphQlTester.document(document2)
                .execute()
                .path("data.bookById.genre")
                .entity(String.class)
                .isEqualTo("Non-Fiction");

        graphQlTester.document(document2)
                .execute()
                .path("data.bookById.author.firstName")
                .entity(String.class)
                .isEqualTo("Donald");

        graphQlTester.document(document2)
                .execute()
                .path("data.bookById.author.lastName")
                .entity(String.class)
                .isEqualTo("Horne");

        // Test 3: Query with minimal fields (only name)
        String document3 = """
                query {
                    bookById(id: "book-1") {
                        name
                    }
                }
                """;

        graphQlTester.document(document3)
                .execute()
                .path("data.bookById.name")
                .entity(String.class)
                .isEqualTo("The Lucky Country");
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void shouldDemonstrateFieldSelectionWithPagination() {
        // Test field selection with pagination - only requesting specific fields
        String document = """
                query {
                    books(first: 2) {
                        edges {
                            cursor
                            node {
                                name
                                genre
                                author {
                                    firstName
                                    lastName
                                }
                            }
                        }
                        pageInfo {
                            hasNextPage
                        }
                        totalCount
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges")
                .entityList(Object.class)
                .hasSize(2);

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges[0].node.name")
                .entity(String.class)
                .isEqualTo("The Lucky Country");

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges[0].node.genre")
                .entity(String.class)
                .isEqualTo("Non-Fiction");

        graphQlTester.document(document)
                .execute()
                .path("data.books.edges[0].node.author.firstName")
                .entity(String.class)
                .isEqualTo("Donald");
    }
}
