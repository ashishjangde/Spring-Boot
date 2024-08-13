package com.example.oauthandsessionmanagment.services.serviceImplementation;

import com.example.oauthandsessionmanagment.dto.PostDto;
import com.example.oauthandsessionmanagment.entities.PostEntity;
import com.example.oauthandsessionmanagment.entities.UserEntity;
import com.example.oauthandsessionmanagment.repositories.PostRepositories;
import com.example.oauthandsessionmanagment.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImplementation implements PostService {
    private final PostRepositories postRepositories;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
        List<PostEntity> postEntities = postRepositories.findAll();
        return postEntities
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public Optional<PostDto> getPostById(long postId) {
       Optional<PostEntity> postEntity = postRepositories.findById(postId);
        return postEntity.map(entity -> modelMapper.map(entity, PostDto.class));
    }

    @Override
    public PostDto savePost(PostDto post) {
        PostEntity postEntity = modelMapper.map(post, PostEntity.class);
        postRepositories.save(postEntity);
        return modelMapper.map(postEntity, PostDto.class);
    }

    @Override
    public PostDto updatePostById(long postId, PostDto post) {
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("user: {}",userEntity.toString());
       PostEntity postEntity = modelMapper.map(post, PostEntity.class);
       postEntity.setId(postId);
       postRepositories.save(postEntity);
       return modelMapper.map(postEntity, PostDto.class);

    }

    @Override
    public PostDto updatePartialPostById(long postId, Map<String,Object> postUpdate) {
        boolean isFound = postRepositories.existsById(postId);
        if(!isFound) return null;
        PostEntity postEntity = postRepositories.findById(postId).orElse(null);
        postUpdate.forEach((key, value) -> {
            Field field = ReflectionUtils.findRequiredField(PostEntity.class, key);
            field.setAccessible(true);
            assert postEntity != null;
            ReflectionUtils.setField(field, postEntity, value);
        });
        assert postEntity != null;
        modelMapper.map(postRepositories.save(postEntity), PostDto.class); // saving the entity
        return modelMapper.map(postEntity, PostDto.class);
    }

    @Override
    public boolean deletePostById(long postId) {
        boolean isFound = postRepositories.findById(postId).isPresent();
        if(isFound) postRepositories.deleteById(postId);
        return true;
    }
}
