package com.microservice.order_service.clients;

import com.microservice.order_service.dto.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service" , path = "/inventory")
public interface InventoryFeignClient {


    @PutMapping("product/reduce-stocks")
    Double reduceStocks(@RequestBody  OrderRequestDto orderRequestDto);
}
