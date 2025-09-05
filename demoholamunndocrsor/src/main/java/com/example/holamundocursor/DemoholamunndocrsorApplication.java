package com.example.holamundocursor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.holamundocursor.repository")
public class DemoholamunndocrsorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoholamunndocrsorApplication.class, args);
	}

}
