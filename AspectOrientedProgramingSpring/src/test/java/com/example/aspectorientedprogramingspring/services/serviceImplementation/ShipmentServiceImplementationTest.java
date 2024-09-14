package com.example.aspectorientedprogramingspring.services.serviceImplementation;

import com.example.aspectorientedprogramingspring.services.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
class ShipmentServiceImplementationTest {

    @Autowired
    private  ShipmentService shipmentService;

    @Test
    void orderPackage() {
       String order =  shipmentService.orderPackage(-9L);
       log.info(order);
    }

    @Test
    void trackPackage() throws Exception {
        shipmentService.trackPackage(1L);
    }
}