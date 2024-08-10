package org.demo.springsecuritybasic.controllers;

import lombok.RequiredArgsConstructor;
import org.demo.springsecuritybasic.dto.PostDto;
import org.demo.springsecuritybasic.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById( @PathVariable long postId) {
      Optional <PostDto> postDto = postService.getPostById(postId);
        return postDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto post = postService.savePost(postDto);
        return ResponseEntity.ok(post);
    }
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable long postId ,@RequestBody PostDto postDto) {
        PostDto post = postService.updatePostById(postId,postDto);
        if(post == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(post);
    }
    @PatchMapping("/{postId}")
    public ResponseEntity<PostDto> updatePartialPost(@PathVariable long postId, @RequestBody Map<String,Object> updatePost) {
        PostDto postDto = postService.updatePartialPostById(postId, updatePost);
        if (postDto == null) return  ResponseEntity.notFound().build();
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<PostDto> deletePostById(@PathVariable long postId) {
        boolean success = postService.deletePostById(postId);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
