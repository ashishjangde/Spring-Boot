package com.microservice.inventory_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestItemDto {
    private Long productId;
    private Integer quantity;
}
