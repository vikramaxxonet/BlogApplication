package com.example.BlogApplication.service;

import com.example.BlogApplication.entity.Post;
import com.example.BlogApplication.repository.PostRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    private Post post;
    @BeforeEach
    public void setup(){
        post = new Post();
        post.setTitle("sample title");
        post.setContent("sample cotent");
    }
    @Test
    public  void testCreatePost(){

       when(postRepository.save(post)).thenReturn(post);

       Post createdpost = postService.createPost(post);

       assertNotNull(createdpost);
       assertEquals("sample title",createdpost.getTitle());
    }
    @Test
    public void testGetAllPosts(){
        when(postRepository.findAll()).thenReturn(List.of(post));

        List<Post> posts = postService.getAllPost();

        assertNotNull(posts);
        assertEquals(1,posts.size());
        assertEquals("sample title", posts.get(0).getTitle());
    }

}
