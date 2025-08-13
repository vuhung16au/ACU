package com.acu.restadvanced.controller;

import com.acu.restadvanced.service.ExternalApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controller for external API consumption
 * 
 * This controller demonstrates:
 * - External API consumption with WebClient
 * - Reactive programming with Mono
 * - Error handling for external services
 * - API documentation for external endpoints
 */
@RestController
@RequestMapping("/api/v1/external")
@Tag(name = "External API Integration", description = "APIs for consuming external services")
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    public ExternalApiController(ExternalApiService externalApiService) {
        this.externalApiService = externalApiService;
    }

    /**
     * Get a post by ID from external API
     */
    @GetMapping("/posts/{id}")
    @Operation(summary = "Get external post", description = "Retrieve a post from JSONPlaceholder API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved post"),
        @ApiResponse(responseCode = "404", description = "Post not found"),
        @ApiResponse(responseCode = "500", description = "External API error")
    })
    public ResponseEntity<Map<String, Object>> getExternalPost(
            @Parameter(description = "Post ID") @PathVariable Long id) {
        
        return externalApiService.getPostById(id)
                .map(ResponseEntity::ok)
                .block();
    }

    /**
     * Get all posts from external API
     */
    @GetMapping("/posts")
    @Operation(summary = "Get all external posts", description = "Retrieve all posts from JSONPlaceholder API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved posts"),
        @ApiResponse(responseCode = "500", description = "External API error")
    })
    public ResponseEntity<Map<String, Object>[]> getAllExternalPosts() {
        return externalApiService.getAllPosts()
                .map(ResponseEntity::ok)
                .block();
    }

    /**
     * Create a post via external API
     */
    @PostMapping("/posts")
    @Operation(summary = "Create external post", description = "Create a new post via JSONPlaceholder API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully created post"),
        @ApiResponse(responseCode = "500", description = "External API error")
    })
    public ResponseEntity<Map<String, Object>> createExternalPost(
            @Parameter(description = "Post data") @RequestBody Map<String, Object> postData) {
        
        return externalApiService.createPost(postData)
                .map(ResponseEntity::ok)
                .block();
    }

    /**
     * Get user information from external API
     */
    @GetMapping("/users/{id}")
    @Operation(summary = "Get external user", description = "Retrieve user information from JSONPlaceholder API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "500", description = "External API error")
    })
    public ResponseEntity<Map<String, Object>> getExternalUser(
            @Parameter(description = "User ID") @PathVariable Long id) {
        
        return externalApiService.getUserById(id)
                .map(ResponseEntity::ok)
                .block();
    }

    /**
     * Get comments for a post from external API
     */
    @GetMapping("/posts/{postId}/comments")
    @Operation(summary = "Get post comments", description = "Retrieve comments for a specific post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved comments"),
        @ApiResponse(responseCode = "500", description = "External API error")
    })
    public ResponseEntity<Map<String, Object>[]> getPostComments(
            @Parameter(description = "Post ID") @PathVariable Long postId) {
        
        return externalApiService.getCommentsForPost(postId)
                .map(ResponseEntity::ok)
                .block();
    }

    /**
     * Search posts by title from external API
     */
    @GetMapping("/posts/search")
    @Operation(summary = "Search external posts", description = "Search posts by title from JSONPlaceholder API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved posts"),
        @ApiResponse(responseCode = "500", description = "External API error")
    })
    public ResponseEntity<Map<String, Object>[]> searchExternalPosts(
            @Parameter(description = "Search term") @RequestParam String title) {
        
        return externalApiService.searchPostsByTitle(title)
                .map(ResponseEntity::ok)
                .block();
    }
}
