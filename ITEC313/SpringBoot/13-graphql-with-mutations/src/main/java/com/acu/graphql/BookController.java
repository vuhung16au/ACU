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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private AuthorRepository authorRepository;
    
    @Autowired
    private GenreRepository genreRepository;
    
    @QueryMapping
    public Book bookById(@Argument String id) {
        return bookRepository.findById(id).orElse(null);
    }
    
    @QueryMapping
    public List<Genre> genres() {
        return genreRepository.findAll();
    }
    
    @QueryMapping
    public Genre genreById(@Argument String id) {
        return genreRepository.findById(id).orElse(null);
    }
    
    @QueryMapping
    public BookConnection booksByGenre(@Argument String genreId, @Argument Integer first, @Argument String after) {
        // Default to 10 if first is not specified
        int limit = (first != null) ? Math.min(first, 100) : 10; // Max 100 items per page
        
        Genre genre = genreRepository.findById(genreId).orElse(null);
        if (genre == null) {
            return new BookConnection(List.of(), new PageInfo(false, false, null, null), 0);
        }
        
        // Get books for this genre with pagination
        List<Book> books = genre.getBooks().stream()
                .sorted((b1, b2) -> b1.getId().compareTo(b2.getId()))
                .collect(Collectors.toList());
        
        // Apply cursor-based pagination
        int startIndex = 0;
        if (after != null && !after.isEmpty()) {
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getCursor().equals(after)) {
                    startIndex = i + 1;
                    break;
                }
            }
        }
        
        int endIndex = Math.min(startIndex + limit, books.size());
        List<Book> pageBooks = books.subList(startIndex, endIndex);
        
        // Convert books to edges
        List<BookEdge> edges = pageBooks.stream()
                .map(book -> new BookEdge(book.getCursor(), book))
                .collect(Collectors.toList());
        
        // Create page info
        String startCursor = edges.isEmpty() ? null : edges.get(0).getCursor();
        String endCursor = edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor();
        boolean hasNextPage = endIndex < books.size();
        PageInfo pageInfo = new PageInfo(hasNextPage, after != null && !after.isEmpty(), startCursor, endCursor);
        
        return new BookConnection(edges, pageInfo, books.size());
    }
    
    @QueryMapping
    public BookConnection books(@Argument Integer first, @Argument String after, @Argument String search, @Argument String genre, @Argument BookOrderBy orderBy) {
        // Default to 10 if first is not specified
        int limit = (first != null) ? Math.min(first, 100) : 10; // Max 100 items per page
        
        Page<Book> bookPage;
        if (after != null && !after.isEmpty()) {
            // Find books after the cursor with filters and sorting
            bookPage = getBooksAfterCursorWithFiltersAndSorting(after, search, genre, orderBy, limit);
        } else {
            // First page with filters and sorting
            bookPage = getBooksWithFiltersAndSorting(search, genre, orderBy, limit);
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
    
    private Page<Book> getBooksWithFiltersAndSorting(String search, String genre, BookOrderBy orderBy, int limit) {
        boolean hasSearch = search != null && !search.isEmpty();
        boolean hasGenre = genre != null && !genre.isEmpty();
        
        if (hasSearch && hasGenre) {
            return getBooksWithSearchAndGenreAndSorting(search, genre, orderBy, limit);
        } else if (hasSearch) {
            return getBooksWithSearchAndSorting(search, orderBy, limit);
        } else if (hasGenre) {
            return getBooksWithGenreAndSorting(genre, orderBy, limit);
        } else {
            return getBooksWithSorting(orderBy, limit);
        }
    }
    
    private Page<Book> getBooksAfterCursorWithFiltersAndSorting(String after, String search, String genre, BookOrderBy orderBy, int limit) {
        boolean hasSearch = search != null && !search.isEmpty();
        boolean hasGenre = genre != null && !genre.isEmpty();
        
        if (hasSearch && hasGenre) {
            return getBooksAfterCursorWithSearchAndGenreAndSorting(after, search, genre, orderBy, limit);
        } else if (hasSearch) {
            return getBooksAfterCursorWithSearchAndSorting(after, search, orderBy, limit);
        } else if (hasGenre) {
            return getBooksAfterCursorWithGenreAndSorting(after, genre, orderBy, limit);
        } else {
            return getBooksAfterCursorWithSorting(after, orderBy, limit);
        }
    }
    
    private Page<Book> getBooksWithSorting(BookOrderBy orderBy, int limit) {
        if (orderBy == null) {
            return bookRepository.findBooks(PageRequest.of(0, limit + 1));
        }
        
        switch (orderBy) {
            case NAME:
                return bookRepository.findBooksOrderByName(PageRequest.of(0, limit + 1));
            case PAGE_COUNT:
                return bookRepository.findBooksOrderByPageCount(PageRequest.of(0, limit + 1));
            case GENRE:
                return bookRepository.findBooksOrderByGenre(PageRequest.of(0, limit + 1));
            default:
                return bookRepository.findBooks(PageRequest.of(0, limit + 1));
        }
    }
    
    private Page<Book> getBooksAfterCursorWithSorting(String after, BookOrderBy orderBy, int limit) {
        if (orderBy == null) {
            return bookRepository.findBooksAfterCursor(after, PageRequest.of(0, limit + 1));
        }
        
        switch (orderBy) {
            case NAME:
                return bookRepository.findBooksAfterCursorOrderByName(after, PageRequest.of(0, limit + 1));
            case PAGE_COUNT:
                return bookRepository.findBooksAfterCursorOrderByPageCount(after, PageRequest.of(0, limit + 1));
            case GENRE:
                return bookRepository.findBooksAfterCursorOrderByGenre(after, PageRequest.of(0, limit + 1));
            default:
                return bookRepository.findBooksAfterCursor(after, PageRequest.of(0, limit + 1));
        }
    }
    
    private Page<Book> getBooksWithSearchAndSorting(String search, BookOrderBy orderBy, int limit) {
        if (orderBy == null) {
            return bookRepository.findBooksWithSearch(search, PageRequest.of(0, limit + 1));
        }
        
        switch (orderBy) {
            case NAME:
                return bookRepository.findBooksWithSearchOrderByName(search, PageRequest.of(0, limit + 1));
            case PAGE_COUNT:
                return bookRepository.findBooksWithSearchOrderByPageCount(search, PageRequest.of(0, limit + 1));
            case GENRE:
                return bookRepository.findBooksWithSearchOrderByGenre(search, PageRequest.of(0, limit + 1));
            default:
                return bookRepository.findBooksWithSearch(search, PageRequest.of(0, limit + 1));
        }
    }
    
    private Page<Book> getBooksAfterCursorWithSearchAndSorting(String after, String search, BookOrderBy orderBy, int limit) {
        if (orderBy == null) {
            return bookRepository.findBooksAfterCursorWithSearch(after, search, PageRequest.of(0, limit + 1));
        }
        
        switch (orderBy) {
            case NAME:
                return bookRepository.findBooksAfterCursorWithSearchOrderByName(after, search, PageRequest.of(0, limit + 1));
            case PAGE_COUNT:
                return bookRepository.findBooksAfterCursorWithSearchOrderByPageCount(after, search, PageRequest.of(0, limit + 1));
            case GENRE:
                return bookRepository.findBooksAfterCursorWithSearchOrderByGenre(after, search, PageRequest.of(0, limit + 1));
            default:
                return bookRepository.findBooksAfterCursorWithSearch(after, search, PageRequest.of(0, limit + 1));
        }
    }
    
    private Page<Book> getBooksWithGenreAndSorting(String genre, BookOrderBy orderBy, int limit) {
        if (orderBy == null) {
            return bookRepository.findBooksWithGenre(genre, PageRequest.of(0, limit + 1));
        }
        
        switch (orderBy) {
            case NAME:
                return bookRepository.findBooksWithGenreOrderByName(genre, PageRequest.of(0, limit + 1));
            case PAGE_COUNT:
                return bookRepository.findBooksWithGenreOrderByPageCount(genre, PageRequest.of(0, limit + 1));
            case GENRE:
                return bookRepository.findBooksWithGenreOrderByGenre(genre, PageRequest.of(0, limit + 1));
            default:
                return bookRepository.findBooksWithGenre(genre, PageRequest.of(0, limit + 1));
        }
    }
    
    private Page<Book> getBooksAfterCursorWithGenreAndSorting(String after, String genre, BookOrderBy orderBy, int limit) {
        if (orderBy == null) {
            return bookRepository.findBooksAfterCursorWithGenre(after, genre, PageRequest.of(0, limit + 1));
        }
        
        switch (orderBy) {
            case NAME:
                return bookRepository.findBooksAfterCursorWithGenreOrderByName(after, genre, PageRequest.of(0, limit + 1));
            case PAGE_COUNT:
                return bookRepository.findBooksAfterCursorWithGenreOrderByPageCount(after, genre, PageRequest.of(0, limit + 1));
            case GENRE:
                return bookRepository.findBooksAfterCursorWithGenreOrderByGenre(after, genre, PageRequest.of(0, limit + 1));
            default:
                return bookRepository.findBooksAfterCursorWithGenre(after, genre, PageRequest.of(0, limit + 1));
        }
    }
    
    private Page<Book> getBooksWithSearchAndGenreAndSorting(String search, String genre, BookOrderBy orderBy, int limit) {
        if (orderBy == null) {
            return bookRepository.findBooksWithSearchAndGenre(search, genre, PageRequest.of(0, limit + 1));
        }
        
        switch (orderBy) {
            case NAME:
                return bookRepository.findBooksWithSearchAndGenreOrderByName(search, genre, PageRequest.of(0, limit + 1));
            case PAGE_COUNT:
                return bookRepository.findBooksWithSearchAndGenreOrderByPageCount(search, genre, PageRequest.of(0, limit + 1));
            case GENRE:
                return bookRepository.findBooksWithSearchAndGenreOrderByGenre(search, genre, PageRequest.of(0, limit + 1));
            default:
                return bookRepository.findBooksWithSearchAndGenre(search, genre, PageRequest.of(0, limit + 1));
        }
    }
    
    private Page<Book> getBooksAfterCursorWithSearchAndGenreAndSorting(String after, String search, String genre, BookOrderBy orderBy, int limit) {
        if (orderBy == null) {
            return bookRepository.findBooksAfterCursorWithSearchAndGenre(after, search, genre, PageRequest.of(0, limit + 1));
        }
        
        switch (orderBy) {
            case NAME:
                return bookRepository.findBooksAfterCursorWithSearchAndGenreOrderByName(after, search, genre, PageRequest.of(0, limit + 1));
            case PAGE_COUNT:
                return bookRepository.findBooksAfterCursorWithSearchAndGenreOrderByPageCount(after, search, genre, PageRequest.of(0, limit + 1));
            case GENRE:
                return bookRepository.findBooksAfterCursorWithSearchAndGenreOrderByGenre(after, search, genre, PageRequest.of(0, limit + 1));
            default:
                return bookRepository.findBooksAfterCursorWithSearchAndGenre(after, search, genre, PageRequest.of(0, limit + 1));
        }
    }

    @SchemaMapping
    public Author author(Book book) {
        return authorRepository.findById(book.getAuthorId()).orElse(null);
    }
    
    @SchemaMapping
    public List<Genre> genres(Book book) {
        return genreRepository.findByBookId(book.getId());
    }
    
    @SchemaMapping
    public List<Book> books(Genre genre) {
        return new ArrayList<>(genre.getBooks());
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
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Genre createGenre(@Argument CreateGenreInput input) {
        String id = "genre-" + UUID.randomUUID().toString().substring(0, 8);
        Genre genre = new Genre(id, input.getName(), input.getDescription());
        return genreRepository.save(genre);
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Genre updateGenre(@Argument String id, @Argument UpdateGenreInput input) {
        Genre existingGenre = genreRepository.findById(id).orElse(null);
        if (existingGenre == null) {
            return null;
        }
        
        if (input.getName() != null) {
            existingGenre.setName(input.getName());
        }
        if (input.getDescription() != null) {
            existingGenre.setDescription(input.getDescription());
        }
        
        return genreRepository.save(existingGenre);
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Boolean deleteGenre(@Argument String id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Book addGenreToBook(@Argument String bookId, @Argument String genreId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        Genre genre = genreRepository.findById(genreId).orElse(null);
        
        if (book == null || genre == null) {
            return null;
        }
        
        book.addGenre(genre);
        return bookRepository.save(book);
    }
    
    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Book removeGenreFromBook(@Argument String bookId, @Argument String genreId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        Genre genre = genreRepository.findById(genreId).orElse(null);
        
        if (book == null || genre == null) {
            return null;
        }
        
        book.removeGenre(genre);
        return bookRepository.save(book);
    }
}
