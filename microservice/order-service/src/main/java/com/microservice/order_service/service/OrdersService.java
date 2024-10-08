package com.microservice.order_service.service;


import com.microservice.order_service.dto.OrderRequestDto;
import com.microservice.order_service.entity.OrdersEntity;
import com.microservice.order_service.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final ModelMapper modelMapper;

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders from the database.");
        List<OrdersEntity> ordersRequest = ordersRepository.findAll();

        if (ordersRequest.isEmpty()) {
            log.warn("No orders found in the database.");
        } else {
            log.info("Fetched {} orders.", ordersRequest.size());
        }

        List<OrderRequestDto> orderDtos = ordersRequest.stream()
                .map(orders -> modelMapper.map(orders, OrderRequestDto.class))
                .collect(Collectors.toList());

        log.info("Mapped orders to DTOs.");
        return orderDtos;
    }

    public OrderRequestDto getOrderById(Long id) {
        log.info("Fetching order with ID: {}", id);
        OrdersEntity ordersEntity = ordersRepository.findById(id).orElse(null);

        if (ordersEntity == null) {
            log.warn("Order with ID: {} not found.", id);
            return null;
        }
        OrderRequestDto orderDto = modelMapper.map(ordersEntity, OrderRequestDto.class);
        log.info("Mapped order to DTO: {}", orderDto);

        return orderDto;
    }
}
