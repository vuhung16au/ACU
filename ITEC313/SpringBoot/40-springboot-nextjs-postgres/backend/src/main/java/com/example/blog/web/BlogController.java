package com.example.blog.web;

import com.example.blog.dto.CommentDtos;
import com.example.blog.dto.PostDtos;
import com.example.blog.dto.UserDtos;
import com.example.blog.service.BlogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Blog API")
public class BlogController {
    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/posts")
    public Page<PostDtos.PostResponse> listPosts(@RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size,
                                                 @RequestParam(required = false, name = "sort") String sort) {
        return blogService.getPosts(page, size, sort);
    }

    @GetMapping("/posts/{id}")
    public PostDtos.PostResponse getPost(@PathVariable Long id) {
        return blogService.getPost(id);
    }

    @GetMapping("/posts/{id}/comments")
    public Page<PostDtos.CommentResponse> getCommentsForPost(@PathVariable Long id,
                                                             @RequestParam(required = false) Integer page,
                                                             @RequestParam(required = false) Integer size,
                                                             @RequestParam(required = false, name = "sort") String sort) {
        return blogService.getCommentsForPost(id, page, size, sort);
    }

    @PostMapping("/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDtos.PostResponse createPost(@RequestBody PostDtos.PostCreateRequest req) {
        return blogService.createPost(req);
    }

    @PutMapping("/posts/{id}")
    public PostDtos.PostResponse updatePost(@PathVariable Long id, @RequestBody PostDtos.PostUpdateRequest req) {
        return blogService.updatePost(id, req);
    }

    @DeleteMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable Long id) {
        blogService.deletePost(id);
    }

    @PostMapping("/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDtos.CommentResponse createComment(@RequestBody CommentDtos.CommentCreateRequest req) {
        return blogService.createComment(req);
    }

    @PutMapping("/comments/{id}")
    public PostDtos.CommentResponse updateComment(@PathVariable Long id, @RequestBody CommentDtos.CommentUpdateRequest req) {
        return blogService.updateComment(id, req);
    }

    @DeleteMapping("/comments/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        blogService.deleteComment(id);
    }

    @GetMapping("/users")
    public Page<UserDtos.UserResponse> listUsers(@RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size,
                                                 @RequestParam(required = false, name = "sort") String sort) {
        return blogService.getUsers(page, size, sort);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDtos.UserResponse createUser(@RequestBody UserDtos.UserCreateRequest req) {
        return blogService.createUser(req);
    }

    @GetMapping("/users/{id}/posts")
    public Page<PostDtos.PostResponse> listUserPosts(@PathVariable Long id,
                                                     @RequestParam(required = false) Integer page,
                                                     @RequestParam(required = false) Integer size,
                                                     @RequestParam(required = false, name = "sort") String sort) {
        return blogService.getPostsForUser(id, page, size, sort);
    }
}


