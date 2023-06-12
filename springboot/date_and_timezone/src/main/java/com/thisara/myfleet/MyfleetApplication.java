package com.thisara.myfleet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {
		"com.thisara.controller",
		"com.thisara.service",
		"com.thisara.dao",
		"com.thisara.myfleet",
		"com.thisara.exception"
})
@EnableJpaRepositories(basePackages = {
		"com.thisara.dao"
})
@EntityScan(basePackages = {
		"com.thisara.domain"
})
@SpringBootApplication
public class MyfleetApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyfleetApplication.class, args);
	}
}
