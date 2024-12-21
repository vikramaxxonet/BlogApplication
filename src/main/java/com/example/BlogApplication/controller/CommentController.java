package com.example.BlogApplication.controller;
import com.example.BlogApplication.entity.Comment;
import com.example.BlogApplication.entity.Post;
import com.example.BlogApplication.repository.CommentRepository;
import com.example.BlogApplication.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Comment")

public class CommentController {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @PostMapping("/{postId}")
    public Comment addComment(@PathVariable Long postId,@RequestBody Comment comment){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Id not found"));
        comment.setPost(post);
        return commentRepository.save(comment);
    }
    @GetMapping("/{postId}")
    public List<Comment> getCommentByPost(@PathVariable Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Id not found" + postId));
        return post.getComments();

    }
}
