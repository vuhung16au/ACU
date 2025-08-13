package com.acu.restadvanced.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;

/**
 * Service for consuming external APIs
 * 
 * This service demonstrates:
 * - WebClient usage for HTTP requests
 * - Reactive programming with Mono
 * - Error handling for external API calls
 * - JSON response processing
 */
@Service
public class ExternalApiService {

    private final WebClient webClient;

    public ExternalApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }

    /**
     * Get a post by ID from JSONPlaceholder API
     */
    public Mono<Map<String, Object>> getPostById(Long id) {
        return webClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(WebClientResponseException.NotFound.class, 
                    error -> Mono.error(new RuntimeException("Post not found with ID: " + id)))
                .onErrorResume(WebClientResponseException.class,
                    error -> Mono.error(new RuntimeException("External API error: " + error.getStatusCode())));
    }

    /**
     * Get all posts from JSONPlaceholder API
     */
    public Mono<Map<String, Object>[]> getAllPosts() {
        return webClient.get()
                .uri("/posts")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>[]>() {})
                .onErrorResume(WebClientResponseException.class,
                    error -> Mono.error(new RuntimeException("External API error: " + error.getStatusCode())));
    }

    /**
     * Create a new post via JSONPlaceholder API
     */
    public Mono<Map<String, Object>> createPost(Map<String, Object> postData) {
        return webClient.post()
                .uri("/posts")
                .bodyValue(postData)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(WebClientResponseException.class,
                    error -> Mono.error(new RuntimeException("External API error: " + error.getStatusCode())));
    }

    /**
     * Get user information by ID
     */
    public Mono<Map<String, Object>> getUserById(Long id) {
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .onErrorResume(WebClientResponseException.NotFound.class,
                    error -> Mono.error(new RuntimeException("User not found with ID: " + id)))
                .onErrorResume(WebClientResponseException.class,
                    error -> Mono.error(new RuntimeException("External API error: " + error.getStatusCode())));
    }

    /**
     * Get comments for a specific post
     */
    public Mono<Map<String, Object>[]> getCommentsForPost(Long postId) {
        return webClient.get()
                .uri("/posts/{postId}/comments", postId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>[]>() {})
                .onErrorResume(WebClientResponseException.class,
                    error -> Mono.error(new RuntimeException("External API error: " + error.getStatusCode())));
    }

    /**
     * Search posts by title
     */
    public Mono<Map<String, Object>[]> searchPostsByTitle(String title) {
        return webClient.get()
                .uri("/posts?title_like={title}", title)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>[]>() {})
                .onErrorResume(WebClientResponseException.class,
                    error -> Mono.error(new RuntimeException("External API error: " + error.getStatusCode())));
    }
}
