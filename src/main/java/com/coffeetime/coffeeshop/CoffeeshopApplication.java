package com.coffeetime.coffeeshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.*") 
@ComponentScan(basePackages = { "com.*" }) 
@EntityScan(basePackages="domain")
public class CoffeeshopApplication extends ServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(CoffeeshopApplication.class, args);
	}

}
