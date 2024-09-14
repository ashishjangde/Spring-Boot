package com.example.aspectorientedprogramingspring.services.serviceImplementation;

import com.example.aspectorientedprogramingspring.aspects.CustomAspectAnnotation;
import com.example.aspectorientedprogramingspring.services.ShipmentService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShipmentServiceImplementation implements ShipmentService {

    @Override
    @CustomAspectAnnotation
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
    public String trackPackage(long id) throws Exception {
      //  log.info("started track package");
        try {
            log.info("Track package of {} has been created", id);
            throw new RuntimeException("Track package failed");
        }catch (Exception e){
           throw new Exception("Track package failed");
        }
    }
}
