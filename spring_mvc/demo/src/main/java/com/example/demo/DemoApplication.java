package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.example.demo.service.productService;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.demo")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// Automatically calls the method at startup
	@Bean
	public CommandLineRunner appAop(productService productService) {
		return args -> {
			System.out.println("Triggering AOP method");
			productService.fetchedProduct();
			System.out.println("AOP execution done");
		};
	}

}
