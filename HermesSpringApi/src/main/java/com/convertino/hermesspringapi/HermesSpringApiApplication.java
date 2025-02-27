package com.convertino.hermesspringapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class HermesSpringApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HermesSpringApiApplication.class, args);
    }

}
