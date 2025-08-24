package com.acu.graphql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    
    List<Review> findByBookId(String bookId);
    
    List<Review> findByUserId(Long userId);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.bookId = :bookId")
    Double getAverageRatingByBookId(@Param("bookId") String bookId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.bookId = :bookId")
    Long getReviewCountByBookId(@Param("bookId") String bookId);
    
    boolean existsByBookIdAndUserId(String bookId, Long userId);
}
