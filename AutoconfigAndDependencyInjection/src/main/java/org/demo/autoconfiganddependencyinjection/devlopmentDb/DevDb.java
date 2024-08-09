package org.demo.autoconfiganddependencyinjection.devlopmentDb;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.demo.autoconfiganddependencyinjection.db.DB;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;



//@Primary
/* we can mark primary to one component as we know "There is more than one bean of 'DB' type"
 we are using primary, but we are manually coupled to the primary annotation we have to loosely coupled,
 so we don't use primary */

@ConditionalOnProperty(name = "db.env", havingValue = "development")
@Component
public class DevDb implements DB {
    @Override
    public String getData() {
        return "Using DevData";
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Constructing DevData");
    }

    @PreDestroy
    public void shutdown() {
        System.out.println("Shutting down DevData");
    }
}
