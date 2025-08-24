package com.acu.graphql;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor ORDER BY b.cursor ASC")
    Page<Book> findBooksAfterCursor(@Param("cursor") String cursor, Pageable pageable);
    
    @Query("SELECT b FROM Book b ORDER BY b.cursor ASC")
    Page<Book> findBooks(Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY b.cursor ASC")
    Page<Book> findBooksAfterCursorWithSearch(@Param("cursor") String cursor, @Param("search") String search, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY b.cursor ASC")
    Page<Book> findBooksWithSearch(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.cursor ASC")
    Page<Book> findBooksAfterCursorWithGenre(@Param("cursor") String cursor, @Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.genre) = LOWER(:genre) ORDER BY b.cursor ASC")
    Page<Book> findBooksWithGenre(@Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.cursor ASC")
    Page<Book> findBooksAfterCursorWithSearchAndGenre(@Param("cursor") String cursor, @Param("search") String search, @Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.cursor ASC")
    Page<Book> findBooksWithSearchAndGenre(@Param("search") String search, @Param("genre") String genre, Pageable pageable);
    
    // Sorting methods
    @Query("SELECT b FROM Book b ORDER BY b.name ASC")
    Page<Book> findBooksOrderByName(Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor ORDER BY b.name ASC")
    Page<Book> findBooksAfterCursorOrderByName(@Param("cursor") String cursor, Pageable pageable);
    
    @Query("SELECT b FROM Book b ORDER BY b.pageCount ASC")
    Page<Book> findBooksOrderByPageCount(Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor ORDER BY b.pageCount ASC")
    Page<Book> findBooksAfterCursorOrderByPageCount(@Param("cursor") String cursor, Pageable pageable);
    
    @Query("SELECT b FROM Book b ORDER BY b.genre ASC")
    Page<Book> findBooksOrderByGenre(Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor ORDER BY b.genre ASC")
    Page<Book> findBooksAfterCursorOrderByGenre(@Param("cursor") String cursor, Pageable pageable);
    
    // Search with sorting
    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY b.name ASC")
    Page<Book> findBooksWithSearchOrderByName(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY b.name ASC")
    Page<Book> findBooksAfterCursorWithSearchOrderByName(@Param("cursor") String cursor, @Param("search") String search, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY b.pageCount ASC")
    Page<Book> findBooksWithSearchOrderByPageCount(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY b.pageCount ASC")
    Page<Book> findBooksAfterCursorWithSearchOrderByPageCount(@Param("cursor") String cursor, @Param("search") String search, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY b.genre ASC")
    Page<Book> findBooksWithSearchOrderByGenre(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) ORDER BY b.genre ASC")
    Page<Book> findBooksAfterCursorWithSearchOrderByGenre(@Param("cursor") String cursor, @Param("search") String search, Pageable pageable);
    
    // Genre with sorting
    @Query("SELECT b FROM Book b WHERE LOWER(b.genre) = LOWER(:genre) ORDER BY b.name ASC")
    Page<Book> findBooksWithGenreOrderByName(@Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.name ASC")
    Page<Book> findBooksAfterCursorWithGenreOrderByName(@Param("cursor") String cursor, @Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.genre) = LOWER(:genre) ORDER BY b.pageCount ASC")
    Page<Book> findBooksWithGenreOrderByPageCount(@Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.pageCount ASC")
    Page<Book> findBooksAfterCursorWithGenreOrderByPageCount(@Param("cursor") String cursor, @Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.genre) = LOWER(:genre) ORDER BY b.genre ASC")
    Page<Book> findBooksWithGenreOrderByGenre(@Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.genre ASC")
    Page<Book> findBooksAfterCursorWithGenreOrderByGenre(@Param("cursor") String cursor, @Param("genre") String genre, Pageable pageable);
    
    // Search and genre with sorting
    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.name ASC")
    Page<Book> findBooksWithSearchAndGenreOrderByName(@Param("search") String search, @Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.name ASC")
    Page<Book> findBooksAfterCursorWithSearchAndGenreOrderByName(@Param("cursor") String cursor, @Param("search") String search, @Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.pageCount ASC")
    Page<Book> findBooksWithSearchAndGenreOrderByPageCount(@Param("search") String search, @Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.pageCount ASC")
    Page<Book> findBooksAfterCursorWithSearchAndGenreOrderByPageCount(@Param("cursor") String cursor, @Param("search") String search, @Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.genre ASC")
    Page<Book> findBooksWithSearchAndGenreOrderByGenre(@Param("search") String search, @Param("genre") String genre, Pageable pageable);
    
    @Query("SELECT b FROM Book b WHERE b.cursor > :cursor AND LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%')) AND LOWER(b.genre) = LOWER(:genre) ORDER BY b.genre ASC")
    Page<Book> findBooksAfterCursorWithSearchAndGenreOrderByGenre(@Param("cursor") String cursor, @Param("search") String search, @Param("genre") String genre, Pageable pageable);
}
