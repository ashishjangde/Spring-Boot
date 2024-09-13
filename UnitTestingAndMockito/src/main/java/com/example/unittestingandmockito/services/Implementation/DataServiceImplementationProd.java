package com.example.unittestingandmockito.services.Implementation;

import com.example.unittestingandmockito.services.DataService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
//@Profile("prod")
public class DataServiceImplementationProd implements DataService {
    @Override
    public String getData() {
        return "Prod Data";
    }
}
