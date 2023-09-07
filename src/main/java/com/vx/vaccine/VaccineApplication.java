package com.vx.vaccine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author Abhiram
 */
@SpringBootApplication
@EnableJpaRepositories("com.vx.vaccine.repositories")
@EntityScan("com.vx.vaccine.models")
public class VaccineApplication {

    public static void main(String[] args) {
        SpringApplication.run(VaccineApplication.class, args);
    }

}
