package com.acu.hibernate.repository;

import com.acu.hibernate.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    
    // Derived queries
    List<Author> findByNameContainingIgnoreCase(String name);
    
    Optional<Author> findByEmail(String email);
    
    List<Author> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Author> findByBooksIsNotEmpty();
    
    // JPQL queries
    @Query("SELECT a FROM Author a WHERE a.name LIKE %:keyword% OR a.biography LIKE %:keyword%")
    List<Author> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT a FROM Author a JOIN FETCH a.books WHERE a.id = :id")
    Optional<Author> findByIdWithBooks(@Param("id") Long id);
    
    @Query("SELECT a FROM Author a LEFT JOIN FETCH a.books")
    List<Author> findAllWithBooks();
    
    // Native query
    @Query(value = "SELECT * FROM authors WHERE birth_date > :date", nativeQuery = true)
    List<Author> findAuthorsBornAfter(@Param("date") LocalDate date);
    
    // Pagination with EntityGraph
    @EntityGraph(attributePaths = {"books"})
    Page<Author> findAll(Pageable pageable);
    
    @EntityGraph(attributePaths = {"books"})
    Page<Author> findByNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Custom query with count
    @Query("SELECT COUNT(a) FROM Author a WHERE SIZE(a.books) > :minBooks")
    long countAuthorsWithMoreThanBooks(@Param("minBooks") int minBooks);
    
    // Find authors by book count
    @Query("SELECT a FROM Author a WHERE SIZE(a.books) >= :minBooks")
    List<Author> findAuthorsWithAtLeastBooks(@Param("minBooks") int minBooks);
}
