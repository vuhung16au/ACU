package com.example.blog.service;

import com.example.blog.dto.CommentDtos;
import com.example.blog.dto.PostDtos;
import com.example.blog.dto.UserDtos;
import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.CommentRepository;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BlogService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public BlogService(UserRepository userRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    private Pageable pageable(Integer page, Integer size, String sort) {
        int p = page == null ? 0 : page;
        int s = size == null ? 10 : size;
        Sort sortSpec = Sort.by("createdAt").descending();
        if (sort != null && !sort.isBlank()) {
            String[] parts = sort.split(",");
            String prop = parts[0];
            boolean desc = parts.length > 1 && parts[1].equalsIgnoreCase("DESC");
            sortSpec = desc ? Sort.by(prop).descending() : Sort.by(prop).ascending();
        }
        return PageRequest.of(p, s, sortSpec);
    }

    public Page<PostDtos.PostResponse> getPosts(Integer page, Integer size, String sort) {
        return postRepository.findAll(pageable(page, size, sort)).map(this::toPostResponse);
    }

    public PostDtos.PostResponse getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        PostDtos.PostResponse resp = toPostResponse(post);
        // Fetch comments
        List<PostDtos.CommentResponse> comments = commentRepository.findByPost(post, pageable(0, 100, "createdAt,DESC"))
                .getContent().stream().map(this::toCommentResponse).collect(Collectors.toList());
        resp.comments = comments;
        return resp;
    }

    public Page<PostDtos.CommentResponse> getCommentsForPost(Long postId, Integer page, Integer size, String sort) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return commentRepository.findByPost(post, pageable(page, size, sort)).map(this::toCommentResponse);
    }

    public Page<PostDtos.PostResponse> getPostsForUser(Long userId, Integer page, Integer size, String sort) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return postRepository.findByUser(user, pageable(page, size, sort)).map(this::toPostResponse);
    }

    public Page<UserDtos.UserResponse> getUsers(Integer page, Integer size, String sort) {
        return userRepository.findAll(pageable(page, size, sort)).map(this::toUserResponse);
    }

    public PostDtos.PostResponse createPost(PostDtos.PostCreateRequest req) {
        User user = userRepository.findById(req.userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = new Post();
        post.setTitle(req.title);
        post.setContent(req.content);
        post.setUser(user);
        post = postRepository.save(post);
        return toPostResponse(post);
    }

    public PostDtos.PostResponse updatePost(Long id, PostDtos.PostUpdateRequest req) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        if (req.title != null) post.setTitle(req.title);
        if (req.content != null) post.setContent(req.content);
        return toPostResponse(postRepository.save(post));
    }

    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) throw new ResourceNotFoundException("Post not found");
        postRepository.deleteById(id);
    }

    public PostDtos.CommentResponse createComment(CommentDtos.CommentCreateRequest req) {
        User user = userRepository.findById(req.userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Post post = postRepository.findById(req.postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        Comment c = new Comment();
        c.setContent(req.content);
        c.setUser(user);
        c.setPost(post);
        return toCommentResponse(commentRepository.save(c));
    }

    public PostDtos.CommentResponse updateComment(Long id, CommentDtos.CommentUpdateRequest req) {
        Comment c = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        if (req.content != null) c.setContent(req.content);
        return toCommentResponse(commentRepository.save(c));
    }

    public void deleteComment(Long id) {
        if (!commentRepository.existsById(id)) throw new ResourceNotFoundException("Comment not found");
        commentRepository.deleteById(id);
    }

    public UserDtos.UserResponse createUser(UserDtos.UserCreateRequest req) {
        User u = new User();
        u.setName(req.name);
        u.setEmail(req.email);
        u.setPassword(req.password);
        u = userRepository.save(u);
        return toUserResponse(u);
    }

    private PostDtos.PostResponse toPostResponse(Post p) {
        PostDtos.PostResponse dto = new PostDtos.PostResponse();
        dto.id = p.getId();
        dto.title = p.getTitle();
        dto.content = p.getContent();
        if (p.getUser() != null) {
            dto.userId = p.getUser().getId();
            dto.userName = p.getUser().getName();
        }
        dto.createdAt = p.getCreatedAt();
        dto.updatedAt = p.getUpdatedAt();
        return dto;
    }

    private PostDtos.CommentResponse toCommentResponse(Comment c) {
        PostDtos.CommentResponse dto = new PostDtos.CommentResponse();
        dto.id = c.getId();
        dto.content = c.getContent();
        if (c.getUser() != null) {
            dto.userId = c.getUser().getId();
            dto.userName = c.getUser().getName();
        }
        dto.createdAt = c.getCreatedAt();
        return dto;
    }

    private UserDtos.UserResponse toUserResponse(User u) {
        UserDtos.UserResponse dto = new UserDtos.UserResponse();
        dto.id = u.getId();
        dto.name = u.getName();
        dto.email = u.getEmail();
        return dto;
    }

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) { super(message); }
    }
}


