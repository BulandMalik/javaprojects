package com.example.buland.springboot.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application.java is the entry point into the app. It’s pretty simple in this case, and in many cases. The most important thing is
 * the @SpringBootApplication annotation, which tells your Spring dependencies to bootstrap the whole Spring Boot framework.
 * There is, of course, also the main() method, which is where the Application class is loaded and run by Spring.
 */
@SpringBootApplication
//@EnableWebMvc
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
