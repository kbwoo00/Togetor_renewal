package com.togetor_renewal.togetor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TogetorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TogetorApplication.class, args);
	}

}
