package com.acu.hibernate.repository;

import com.acu.hibernate.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Derived queries
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    List<Book> findByAuthorId(Long authorId);
    
    List<Book> findByPublicationYear(Integer publicationYear);
    
    List<Book> findByPriceGreaterThan(BigDecimal price);
    
    List<Book> findByPublicationYearBetween(Integer startYear, Integer endYear);
    
    List<Book> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    Optional<Book> findByIsbn(String isbn);
    
    List<Book> findByPageCountGreaterThan(Integer pageCount);
    
    // JPQL queries
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:keyword% OR b.description LIKE %:keyword%")
    List<Book> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT b FROM Book b JOIN FETCH b.author WHERE b.id = :id")
    Optional<Book> findByIdWithAuthor(@Param("id") Long id);
    
    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.tags WHERE b.id = :id")
    Optional<Book> findByIdWithTags(@Param("id") Long id);
    
    @Query("SELECT b FROM Book b JOIN FETCH b.author LEFT JOIN FETCH b.tags WHERE b.id = :id")
    Optional<Book> findByIdWithAuthorAndTags(@Param("id") Long id);
    
    @Query("SELECT b FROM Book b JOIN FETCH b.author WHERE b.author.id = :authorId")
    List<Book> findByAuthorIdWithAuthor(@Param("authorId") Long authorId);
    
    // Native query
    @Query(value = "SELECT * FROM books WHERE publication_year > :year ORDER BY publication_year DESC", nativeQuery = true)
    List<Book> findBooksPublishedAfter(@Param("year") Integer year);
    
    // Pagination with EntityGraph
    @EntityGraph(attributePaths = {"author"})
    Page<Book> findAll(Pageable pageable);
    
    @EntityGraph(attributePaths = {"author", "tags"})
    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    
    // Complex queries
    @Query("SELECT b FROM Book b JOIN FETCH b.author WHERE b.price >= :minPrice AND b.publicationYear >= :minYear")
    List<Book> findExpensiveRecentBooks(@Param("minPrice") BigDecimal minPrice, @Param("minYear") Integer minYear);
    
    @Query("SELECT b FROM Book b WHERE SIZE(b.tags) >= :minTags")
    List<Book> findBooksWithAtLeastTags(@Param("minTags") int minTags);
    
    // Statistics queries
    @Query("SELECT AVG(b.price) FROM Book b WHERE b.author.id = :authorId")
    BigDecimal getAveragePriceByAuthor(@Param("authorId") Long authorId);
    
    @Query("SELECT COUNT(b) FROM Book b WHERE b.publicationYear = :year")
    long countBooksByYear(@Param("year") Integer year);
    
    // Find books by tag
    @Query("SELECT DISTINCT b FROM Book b JOIN b.tags t WHERE t.name = :tagName")
    List<Book> findByTagName(@Param("tagName") String tagName);
    
    // Find books by multiple tags
    @Query("SELECT DISTINCT b FROM Book b JOIN b.tags t WHERE t.name IN :tagNames")
    List<Book> findByTagNames(@Param("tagNames") List<String> tagNames);
}
