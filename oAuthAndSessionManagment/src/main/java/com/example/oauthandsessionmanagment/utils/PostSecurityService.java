package com.example.oauthandsessionmanagment.utils;

import com.example.oauthandsessionmanagment.dto.PostDto;
import com.example.oauthandsessionmanagment.entities.UserEntity;
import com.example.oauthandsessionmanagment.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurityService {
    private final PostService postService;

    public boolean isOwnerOfThePost(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = (UserEntity) authentication.getPrincipal(); // Cast this to your custom UserEntity or extract it properly

        PostDto post = postService.getPostById(postId).orElse(null);

        if (post == null || post.getAuthor() == null) {
            return false;
        }

        return post.getAuthor().getId() == userEntity.getId(); // Compare primitive long values using '=='
    }
}
