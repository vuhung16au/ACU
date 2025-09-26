package com.example.blog.dto;

public class CommentDtos {
    public static class CommentCreateRequest {
        public String content;
        public Long userId;
        public Long postId;
    }

    public static class CommentUpdateRequest {
        public String content;
    }
}


