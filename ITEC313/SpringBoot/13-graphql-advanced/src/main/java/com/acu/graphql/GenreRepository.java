package com.acu.graphql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, String> {
    
    Optional<Genre> findByName(String name);
    
    List<Genre> findByNameContainingIgnoreCase(String name);
    
    @Query("SELECT g FROM Genre g JOIN g.books b WHERE b.id = :bookId")
    List<Genre> findByBookId(@Param("bookId") String bookId);
    
    @Query("SELECT g FROM Genre g WHERE g.name IN :names")
    List<Genre> findByNames(@Param("names") List<String> names);
}
