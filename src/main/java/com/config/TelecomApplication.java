package com.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.config")
public class TelecomApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelecomApplication.class, args);
	}
}
