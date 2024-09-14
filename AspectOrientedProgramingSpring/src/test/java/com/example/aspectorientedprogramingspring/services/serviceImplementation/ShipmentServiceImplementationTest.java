package com.example.aspectorientedprogramingspring.services.serviceImplementation;

import com.example.aspectorientedprogramingspring.services.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ShipmentServiceImplementationTest {

    @Autowired
    private  ShipmentService shipmentService;

    @Test
    void orderPackage() {
        shipmentService.orderPackage(4L);
    }

    @Test
    void trackPackage() {
        shipmentService.trackPackage(1L);
    }
}