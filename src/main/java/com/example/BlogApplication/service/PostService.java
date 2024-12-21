package com.example.BlogApplication.service;

import com.example.BlogApplication.entity.Post;
import com.example.BlogApplication.repository.PostRepository;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.github.benmanes.caffeine.cache.Cache;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class PostService {

    private final Cache<Long, Post> postCache;

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;

        this.postCache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
    }


    public List<Post> getAllPost() {
        return postRepository.findAll();
    }


    public Post createPost(Post post) {
        return postRepository.save(post);
    }


    public Post getById(Long id) {
        Post post = postCache.getIfPresent(id);
        if (post == null) {
            post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
            postCache.put(id, post);
        }
        return post;
    }

}

