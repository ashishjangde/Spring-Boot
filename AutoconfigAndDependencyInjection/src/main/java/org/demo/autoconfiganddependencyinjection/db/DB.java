package org.demo.autoconfiganddependencyinjection.db;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public interface DB {
     String getData();


}
