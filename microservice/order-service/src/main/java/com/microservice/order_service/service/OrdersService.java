package com.microservice.order_service.service;


import com.microservice.order_service.clients.InventoryFeignClient;
import com.microservice.order_service.dto.OrderRequestDto;
import com.microservice.order_service.entity.OrderStatus;
import com.microservice.order_service.entity.OrdersEntity;
import com.microservice.order_service.entity.OrdersItemEntity;
import com.microservice.order_service.repositories.OrdersRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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
    private final InventoryFeignClient inventoryFeignClient;

    public List<OrderRequestDto> getAllOrders() {
        log.info("Fetching all orders from the database.");
        List<OrdersEntity> ordersRequest = ordersRepository.findAll();

        if (ordersRequest.isEmpty()) {
            log.warn("No orders found in the database.");
        } else {
            log.info("Fetched {} orders.", ordersRequest.size());
        }

        List<OrderRequestDto> orderDto = ordersRequest.stream()
                .map(orders -> modelMapper.map(orders, OrderRequestDto.class))
                .collect(Collectors.toList());

        log.info("Mapped orders to DTOs.");
        return orderDto;
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

//    @Retry(name = "inventoryRetry" , fallbackMethod = "createOrderFallback")
//    @RateLimiter(name = "inventoryRateLimiter" , fallbackMethod = "createOrderFallback")
    @CircuitBreaker(name = "inventoryCircuitBreaker" , fallbackMethod = "createOrderFallback")
    public OrderRequestDto createOrder(OrderRequestDto order) {
        log.info("calling the create Order method");
        Double totalPrice = inventoryFeignClient.reduceStocks(order);
        OrdersEntity ordersEntity = modelMapper.map(order , OrdersEntity.class);
        for (OrdersItemEntity ordersItemEntity : ordersEntity.getItems()){
            ordersItemEntity.setOrder(ordersEntity);
        }
        ordersEntity.setTotalPrice(totalPrice);
        ordersEntity.setOrderStatus(OrderStatus.CONFIRMED);
       OrdersEntity savedOrder = ordersRepository.save(ordersEntity);
        return modelMapper.map(savedOrder , OrderRequestDto.class);
    }

    public  OrderRequestDto createOrderFallback(OrderRequestDto order , Throwable throwable){
        log.info("Fallback Occurred due too {}", throwable.getMessage());
        return new OrderRequestDto();
    }
}
