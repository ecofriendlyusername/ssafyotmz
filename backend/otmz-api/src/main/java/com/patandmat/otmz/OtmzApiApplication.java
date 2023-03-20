package com.patandmat.otmz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OtmzApiApplication {

    public static void main(String[] args) {
        System.out.println("hello");
        SpringApplication.run(OtmzApiApplication.class, args);
    }

}
