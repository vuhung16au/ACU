package com.example.blog.repository;

import com.example.blog.model.Comment;
import com.example.blog.model.Post;
import com.example.blog.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost(Post post, Pageable pageable);
    Page<Comment> findByUser(User user, Pageable pageable);
}


