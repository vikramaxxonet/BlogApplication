package com.example.BlogApplication.service;


import com.example.BlogApplication.entity.Comment;
import com.example.BlogApplication.entity.Post;
import com.example.BlogApplication.repository.CommentRepository;
import com.example.BlogApplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public Comment addCommentToPost(Long postId, Comment comment) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            comment.setPost(post);
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Post not found with id: " + postId);
        }
    }
    public List<Comment> getCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));
        return post.getComments() != null ? post.getComments() : new ArrayList<>();
    }



}


