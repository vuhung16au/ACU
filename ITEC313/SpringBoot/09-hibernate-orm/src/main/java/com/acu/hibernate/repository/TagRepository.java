package com.acu.hibernate.repository;

import com.acu.hibernate.entity.Tag;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    // Derived queries
    Optional<Tag> findByName(String name);
    
    List<Tag> findByNameContainingIgnoreCase(String name);
    
    List<Tag> findByDescriptionContainingIgnoreCase(String description);
    
    // JPQL queries
    @Query("SELECT t FROM Tag t WHERE t.name LIKE %:keyword% OR t.description LIKE %:keyword%")
    List<Tag> searchByKeyword(@Param("keyword") String keyword);
    
    @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.books WHERE t.id = :id")
    Optional<Tag> findByIdWithBooks(@Param("id") Long id);
    
    @Query("SELECT t FROM Tag t LEFT JOIN FETCH t.books")
    List<Tag> findAllWithBooks();
    
    // Find tags by book count
    @Query("SELECT t FROM Tag t WHERE SIZE(t.books) >= :minBooks")
    List<Tag> findTagsWithAtLeastBooks(@Param("minBooks") int minBooks);
    
    @Query("SELECT t FROM Tag t WHERE SIZE(t.books) = 0")
    List<Tag> findUnusedTags();
    
    // Find most popular tags
    @Query("SELECT t FROM Tag t ORDER BY SIZE(t.books) DESC")
    List<Tag> findAllOrderByBookCountDesc();
    
    // Native query
    @Query(value = "SELECT t.* FROM tags t JOIN book_tags bt ON t.id = bt.tag_id GROUP BY t.id ORDER BY COUNT(bt.book_id) DESC", nativeQuery = true)
    List<Tag> findAllOrderByBookCountDescNative();
    
    // EntityGraph for eager loading
    @EntityGraph(attributePaths = {"books"})
    List<Tag> findAll();
    
    @EntityGraph(attributePaths = {"books"})
    Optional<Tag> findById(Long id);
    
    // Statistics
    @Query("SELECT COUNT(t) FROM Tag t WHERE SIZE(t.books) > 0")
    long countUsedTags();
    
    @Query("SELECT t.name, COUNT(t.books) FROM Tag t GROUP BY t.name ORDER BY COUNT(t.books) DESC")
    List<Object[]> getTagUsageStatistics();
}
