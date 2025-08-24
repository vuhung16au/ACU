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
}
