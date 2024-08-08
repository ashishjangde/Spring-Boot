package org.demo.autoconfiganddependencyinjection;

import org.demo.autoconfiganddependencyinjection.db.DB;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AutoconfigAndDependencyInjectionApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AutoconfigAndDependencyInjectionApplication.class, args);
    }

/*
    @Autowired
    public DB db;
*/


    /**
     * constructor-based injection in which we can final our property that no one can change
     * we can use autowired too // but not recommended depend upon use cases
     */
    private final DB db;
    public  AutoconfigAndDependencyInjectionApplication(DB db) {
        this.db = db;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(db.getData());
    }
}
