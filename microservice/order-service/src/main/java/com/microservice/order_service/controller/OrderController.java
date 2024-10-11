package com.microservice.order_service.controller;


import com.microservice.order_service.clients.InventoryFeignClient;
import com.microservice.order_service.dto.OrderRequestDto;
import com.microservice.order_service.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/core")
public class OrderController {

    private final OrdersService ordersService;


    @GetMapping("/helloOrder")
    public ResponseEntity<String> sayHello() {
       return ResponseEntity.ok("Hello Order Service");
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
