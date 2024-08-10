package org.demo.springsecuritybasic.repositories;

import org.demo.springsecuritybasic.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepositories extends JpaRepository< PostEntity,Long> {
}
