package com.example.springbootsecurityjwt.repositories;


import com.example.springbootsecurityjwt.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositories extends JpaRepository<PostEntity,Long> {
}
