package com.example.oauthandsessionmanagment.controllers;


import com.example.oauthandsessionmanagment.dto.PostDto;
import com.example.oauthandsessionmanagment.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;


    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    @PreAuthorize("@postSecurityService.isOwnerOfThePost(#postId)")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        Optional<PostDto> post = postService.getPostById(postId);
        return post.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole({'CREATOR','ADMIN'} AND hasAuthority('POST_CREATE'))")  //similar to @Secured
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto post) {
        PostDto createdPost = postService.savePost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable Long postId, @RequestBody PostDto post) {
        PostDto updatedPost = postService.updatePostById(postId, post);
        if (updatedPost != null) {
            return new ResponseEntity<>(updatedPost, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostDto> patchPostById(@PathVariable Long postId, @RequestBody Map<String, Object> postUpdate) {
        PostDto postDto = postService.updatePartialPostById(postId, postUpdate);
        if (postDto != null) {
            return new ResponseEntity<>(postDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePostById(@PathVariable(name = "postId") long postId) {
        boolean deleted = postService.deletePostById(postId);
        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
