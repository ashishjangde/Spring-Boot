package com.microservice.inventory_service.controller;
import com.microservice.inventory_service.clients.OrdersFeignClient;
import com.microservice.inventory_service.dto.OrderRequestDto;
import com.microservice.inventory_service.dto.ProductDto;
import com.microservice.inventory_service.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;  //import org.springframework.cloud.client.discovery.DiscoveryClient;
                                                    // must remember always from this package
    private final RestClient restClient;

    private final OrdersFeignClient ordersFeignClient;

    @GetMapping("/fetchOrder")
    public ResponseEntity<String> fetchFromOrderService(HttpServletRequest request) {
       log.info(request.getHeader("X-Custom-Header"));
//        ServiceInstance serviceInstance = discoveryClient.getInstances("order-service").stream().findFirst().orElse(null);
//        if (serviceInstance == null) {
//            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Order service not available");
//        }
//        String url = serviceInstance.getUri().toString() + "/orders/core/helloOrder";
//        String response = restClient.get()
//                .uri(url)
//                .retrieve()
//                .body(String.class);
//        return ResponseEntity.ok(response);  //we dont need any stuff we're using feignClient
        String response = ordersFeignClient.getHello();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("reduce-stocks")
    public ResponseEntity<Double>  reduceStocks(@RequestBody OrderRequestDto orderRequestDto) {
       Double totalPrice = productService.reduceStocks(orderRequestDto);
       return ResponseEntity.ok(totalPrice);
    }

}
