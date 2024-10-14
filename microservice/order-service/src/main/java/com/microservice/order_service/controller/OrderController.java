package com.microservice.order_service.controller;

import com.microservice.order_service.dto.OrderRequestDto;
import com.microservice.order_service.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RefreshScope
@RequiredArgsConstructor
@Controller
@RequestMapping("/core")
public class OrderController {

    private final OrdersService ordersService;
    @Value("${my.profile}")
    private String myProfile;

    @GetMapping("/profile")
    public ResponseEntity<String> profile() {
        return ResponseEntity.ok(myProfile);
    }


    @GetMapping("/helloOrder")
    public ResponseEntity<String> sayHello(@RequestHeader("X-USER-ID") Long userId) {
       return ResponseEntity.ok("Hello Order Service userID : " + userId);
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders() {
        List<OrderRequestDto> orders = ordersService.getAllOrders();
        return ResponseEntity.ok(orders);
    }
    @GetMapping("{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id) {
        OrderRequestDto order = ordersService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto order) {
       OrderRequestDto orderRequestDto = ordersService.createOrder(order);
       if (orderRequestDto == null) return ResponseEntity.notFound().build();
       return ResponseEntity.ok(orderRequestDto);
    }
}
