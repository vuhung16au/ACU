package com.example.blog.dto;

import java.time.Instant;
import java.util.List;

public class PostDtos {
    public static class PostResponse {
        public Long id;
        public String title;
        public String content;
        public Long userId;
        public String userName;
        public Instant createdAt;
        public Instant updatedAt;
        public List<CommentResponse> comments;
    }

    public static class PostCreateRequest {
        public String title;
        public String content;
        public Long userId;
    }

    public static class PostUpdateRequest {
        public String title;
        public String content;
    }

    public static class CommentResponse {
        public Long id;
        public String content;
        public Long userId;
        public String userName;
        public Instant createdAt;
    }
}


