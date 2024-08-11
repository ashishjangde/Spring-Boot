package com.example.springbootsecurityjwt.services;

import com.example.springbootsecurityjwt.dto.PostDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PostService {
    List<PostDto> getAllPosts();
   Optional <PostDto> getPostById(long postId);
    PostDto savePost(PostDto post);
    PostDto updatePostById(long postId, PostDto post);
    PostDto updatePartialPostById(long postId, Map<String,Object> postUpdate);
    boolean deletePostById(long postId);

}
