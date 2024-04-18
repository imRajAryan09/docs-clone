package com.google.docs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DocsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocsApplication.class, args);
	}

}
