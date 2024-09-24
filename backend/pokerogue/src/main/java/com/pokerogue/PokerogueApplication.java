package com.pokerogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PokerogueApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokerogueApplication.class, args);
	}

}
