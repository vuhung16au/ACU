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
}
