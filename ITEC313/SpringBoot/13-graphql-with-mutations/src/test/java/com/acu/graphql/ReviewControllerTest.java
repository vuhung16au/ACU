package com.acu.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@GraphQlTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "313@acu.com", roles = {"ADMIN"})
    void shouldQueryReviewsByBook() {
        // Given
        Review review1 = new Review("review-1", "book-1", 1L, 5, "Great book!");
        Review review2 = new Review("review-2", "book-1", 1L, 4, "Good read");
        List<Review> reviews = Arrays.asList(review1, review2);
        
        when(reviewRepository.findByBookId("book-1")).thenReturn(reviews);

        // When & Then
        String document = """
                query {
                    reviewsByBook(bookId: "book-1") {
                        id
                        bookId
                        userId
                        rating
                        comment
                        createdAt
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.reviewsByBook")
                .entityList(Review.class)
                .hasSize(2);
    }

    @Test
    @WithMockUser(username = "313@acu.com", roles = {"ADMIN"})
    void shouldQueryReviewsByUser() {
        // Given
        Review review1 = new Review("review-1", "book-1", 1L, 5, "Great book!");
        Review review2 = new Review("review-2", "book-2", 1L, 4, "Good read");
        List<Review> reviews = Arrays.asList(review1, review2);
        
        when(reviewRepository.findByUserId(1L)).thenReturn(reviews);

        // When & Then
        String document = """
                query {
                    reviewsByUser(userId: "1") {
                        id
                        bookId
                        userId
                        rating
                        comment
                        createdAt
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.reviewsByUser")
                .entityList(Review.class)
                .hasSize(2);
    }

    @Test
    @WithMockUser(username = "313@acu.com", roles = {"ADMIN"})
    void shouldQueryReviewById() {
        // Given
        Review review = new Review("review-1", "book-1", 1L, 5, "Great book!");
        
        when(reviewRepository.findById("review-1")).thenReturn(Optional.of(review));

        // When & Then
        String document = """
                query {
                    reviewById(id: "review-1") {
                        id
                        bookId
                        userId
                        rating
                        comment
                        createdAt
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.reviewById.id")
                .entity(String.class)
                .isEqualTo("review-1");
    }

    @Test
    @WithMockUser(username = "313@acu.com", roles = {"ADMIN"})
    void shouldCreateReview() {
        // Given
        User user = new User("313@acu.com", "password", "ADMIN");
        user.setId(1L);
        Book book = new Book("book-1", "Test Book", 300, "author-1", "Fiction");
        
        Review savedReview = new Review("review-1", "book-1", 1L, 5, "Great book!");
        
        when(userRepository.findByUsername("313@acu.com")).thenReturn(Optional.of(user));
        when(bookRepository.findById("book-1")).thenReturn(Optional.of(book));
        when(reviewRepository.existsByBookIdAndUserId("book-1", 1L)).thenReturn(false);
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // When & Then
        String document = """
                mutation {
                    createReview(input: {
                        bookId: "book-1"
                        rating: 5
                        comment: "Great book!"
                    }) {
                        id
                        bookId
                        userId
                        rating
                        comment
                        createdAt
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.createReview.id")
                .entity(String.class)
                .isEqualTo("review-1");
    }

    @Test
    @WithMockUser(username = "313@acu.com", roles = {"ADMIN"})
    void shouldUpdateReview() {
        // Given
        User user = new User("313@acu.com", "password", "ADMIN");
        user.setId(1L);
        Review existingReview = new Review("review-1", "book-1", 1L, 4, "Good book");
        Review updatedReview = new Review("review-1", "book-1", 1L, 5, "Great book!");
        
        when(userRepository.findByUsername("313@acu.com")).thenReturn(Optional.of(user));
        when(reviewRepository.findById("review-1")).thenReturn(Optional.of(existingReview));
        when(reviewRepository.save(any(Review.class))).thenReturn(updatedReview);

        // When & Then
        String document = """
                mutation {
                    updateReview(id: "review-1", input: {
                        rating: 5
                        comment: "Great book!"
                    }) {
                        id
                        rating
                        comment
                        createdAt
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.updateReview.rating")
                .entity(Integer.class)
                .isEqualTo(5);
    }

    @Test
    @WithMockUser(username = "313@acu.com", roles = {"ADMIN"})
    void shouldDeleteReview() {
        // Given
        User user = new User("313@acu.com", "password", "ADMIN");
        user.setId(1L);
        Review review = new Review("review-1", "book-1", 1L, 5, "Great book!");
        
        when(userRepository.findByUsername("313@acu.com")).thenReturn(Optional.of(user));
        when(reviewRepository.findById("review-1")).thenReturn(Optional.of(review));

        // When & Then
        String document = """
                mutation {
                    deleteReview(id: "review-1")
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.deleteReview")
                .entity(Boolean.class)
                .isEqualTo(true);
    }
}
