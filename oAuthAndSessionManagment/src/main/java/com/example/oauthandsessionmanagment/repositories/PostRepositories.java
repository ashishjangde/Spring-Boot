package com.example.oauthandsessionmanagment.repositories;



import com.example.oauthandsessionmanagment.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositories extends JpaRepository<PostEntity,Long> {
}
