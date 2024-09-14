package com.example.aspectorientedprogramingspring.services.serviceImplementation;

import com.example.aspectorientedprogramingspring.aspects.CustomException;
import com.example.aspectorientedprogramingspring.services.ShipmentService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShipmentServiceImplementation implements ShipmentService {

    @Override
    @CustomException
    public String orderPackage(long id) {
      // log.info("started order package");
       try{
           log.info("Order package of {} has been created", id);
           Thread.sleep(500);
       }catch (Exception e){
           log.error(e.getMessage());
       }
       return "Order package successfully created";
    }

    @Override
    @Transactional
    public String trackPackage(long id) {
      //  log.info("started track package");
        try {
            log.info("Track package of {} has been created", id);
            throw new RuntimeException("Track package failed");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return "Track package successfully created";
    }
}
