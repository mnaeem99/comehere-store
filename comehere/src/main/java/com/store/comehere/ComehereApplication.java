package com.store.comehere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.store.comehere"})
public class ComehereApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComehereApplication.class, args);
	}

}

