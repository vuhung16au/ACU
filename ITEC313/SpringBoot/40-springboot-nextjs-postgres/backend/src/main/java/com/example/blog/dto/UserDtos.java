package com.example.blog.dto;

public class UserDtos {
    public static class UserCreateRequest {
        public String name;
        public String email;
        public String password;
    }

    public static class UserResponse {
        public Long id;
        public String name;
        public String email;
    }
}


