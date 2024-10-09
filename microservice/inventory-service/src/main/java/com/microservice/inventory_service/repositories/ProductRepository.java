package com.microservice.inventory_service.repositories;

import com.microservice.inventory_service.controller.ProductController;
import com.microservice.inventory_service.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity , Long> {
}
