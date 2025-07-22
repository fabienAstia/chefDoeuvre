package co.simplon.personalities;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class PersonalitiesBusinessApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalitiesBusinessApplication.class, args);
    }

}
