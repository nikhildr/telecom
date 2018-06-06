package com.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages="com.config")
@EntityScan(basePackages="com.config.entity")
@EnableJpaRepositories
public class TelecomApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelecomApplication.class, args);
	}
}
