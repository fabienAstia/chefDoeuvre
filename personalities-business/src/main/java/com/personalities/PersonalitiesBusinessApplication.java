package com.personalities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class PersonalitiesBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalitiesBusinessApplication.class, args);
    }

}
