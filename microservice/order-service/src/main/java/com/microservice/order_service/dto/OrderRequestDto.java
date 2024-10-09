package com.microservice.order_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Long id;                               // Order ID
    private List<OrderRequestItemDto> items;      // List of items in the order
    private BigDecimal totalPrice;                 // Total price of the order
}
