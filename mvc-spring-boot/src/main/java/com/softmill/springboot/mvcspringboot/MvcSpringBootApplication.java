package com.softmill.springboot.mvcspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MvcSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcSpringBootApplication.class, args);
	}
}
