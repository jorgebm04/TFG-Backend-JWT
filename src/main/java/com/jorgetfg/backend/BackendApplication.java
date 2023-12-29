package com.jorgetfg.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EntityScan(basePackages = {"com.jorgetfg.backend.entities"})
//@ComponentScan({"org.mapstruct", "com.jorgetfg.backend.configuration", "com.jorgetfg.backend.services", "com.jorgetfg.backend.mappers", "com.jorgetfg.backend.repositories"})
//@EnableJpaRepositories(basePackages = "com.jorgetfg.backend.repositories")
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
