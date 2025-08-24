package com.acu.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class ReviewController {
    
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    
    public ReviewController(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }
    
    @QueryMapping
    public List<Review> reviewsByBook(@Argument String bookId) {
        return reviewRepository.findByBookId(bookId);
    }
    
    @QueryMapping
    public List<Review> reviewsByUser(@Argument String userId) {
        return reviewRepository.findByUserId(Long.valueOf(userId));
    }
    
    @QueryMapping
    public Review reviewById(@Argument String id) {
        return reviewRepository.findById(id).orElse(null);
    }
    
    @MutationMapping
    public Review createReview(@Argument CreateReviewInput input) {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Validate book exists
        Book book = bookRepository.findById(input.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        
        // Validate rating
        if (input.getRating() < 1 || input.getRating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }
        
        // Check if user already reviewed this book
        if (reviewRepository.existsByBookIdAndUserId(input.getBookId(), user.getId())) {
            throw new RuntimeException("User has already reviewed this book");
        }
        
        Review review = new Review(
                "review-" + UUID.randomUUID().toString(),
                input.getBookId(),
                user.getId(),
                input.getRating(),
                input.getComment()
        );
        
        return reviewRepository.save(review);
    }
    
    @MutationMapping
    public Review updateReview(@Argument String id, @Argument UpdateReviewInput input) {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Review review = reviewRepository.findById(id).orElse(null);
        
        // If review doesn't exist, return null
        if (review == null) {
            return null;
        }
        
        // Check if user owns the review
        if (!review.getUserId().equals(user.getId())) {
            throw new RuntimeException("User can only update their own reviews");
        }
        
        if (input.getRating() != null) {
            if (input.getRating() < 1 || input.getRating() > 5) {
                throw new RuntimeException("Rating must be between 1 and 5");
            }
            review.setRating(input.getRating());
        }
        
        if (input.getComment() != null) {
            review.setComment(input.getComment());
        }
        
        return reviewRepository.save(review);
    }
    
    @MutationMapping
    public Boolean deleteReview(@Argument String id) {
        // Get current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Review review = reviewRepository.findById(id).orElse(null);
        
        // If review doesn't exist, return false
        if (review == null) {
            return false;
        }
        
        // Check if user owns the review
        if (!review.getUserId().equals(user.getId())) {
            throw new RuntimeException("User can only delete their own reviews");
        }
        
        reviewRepository.delete(review);
        return true;
    }
    
    // Removed getBookReviews method - field-level security is now handled in BookController
    
    @SchemaMapping(typeName = "Book", field = "averageRating")
    public Double getBookAverageRating(Book book) {
        Double avgRating = reviewRepository.getAverageRatingByBookId(book.getId());
        return avgRating != null ? avgRating : 0.0;
    }
    
    @SchemaMapping(typeName = "Book", field = "reviewCount")
    public Long getBookReviewCount(Book book) {
        return reviewRepository.getReviewCountByBookId(book.getId());
    }
    
    @SchemaMapping(typeName = "Review", field = "book")
    public Book getReviewBook(Review review) {
        return bookRepository.findById(review.getBookId()).orElse(null);
    }
    
    @SchemaMapping(typeName = "Review", field = "user")
    public User getReviewUser(Review review) {
        return userRepository.findById(review.getUserId()).orElse(null);
    }
    
    @SchemaMapping(typeName = "User", field = "reviews")
    public List<Review> getUserReviews(User user) {
        return reviewRepository.findByUserId(user.getId());
    }
}
