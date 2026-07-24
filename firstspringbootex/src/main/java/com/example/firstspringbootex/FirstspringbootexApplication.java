package com.example.firstspringbootex;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FirstspringbootexApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstspringbootexApplication.class, args);
	}

	@Bean
	CommandLineRunner initData(InMemoryUserStore userStore) {
		return args -> {
			// Adding test user
			userStore.saveNew("Reliaquest@gmail.org", "password@2026");
			System.out.println("Test user created! Email:Reliaquest@gmail.org | Password:password@2026");
		};
	}

}
