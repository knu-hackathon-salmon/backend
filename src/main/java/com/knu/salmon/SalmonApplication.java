package com.knu.salmon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SalmonApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalmonApplication.class, args);
	}

}
