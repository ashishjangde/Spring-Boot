package org.demo.autoconfiganddependencyinjection.productionDb;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.demo.autoconfiganddependencyinjection.db.DB;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

//@Primary
/* we can mark primary to one component as we know "There is more than one bean of 'DB' type"
 we are using primary, but we are manually coupled to the primary annotation we have to loosely coupled,
 so we don't use primary */

@ConditionalOnProperty(name = "db.env", havingValue = "production")
@Component
public class ProDb implements DB {

    @Override
    public String getData() {
        return "Using ProData";
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("constructing Product DB");
    }

    @PreDestroy
    public void shutdown() {
    System.out.println("shutting down Product DB");
    }

}
