package com.buland.example.springboot.banner.SpringBootBannerExamples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringBootBannerExamplesApplication {

	public static void main(String[] args) {

		//SpringApplication.run(SpringBootBannerExamplesApplication.class, args);

		new SpringApplicationBuilder(SpringBootBannerExamplesApplication.class)
				.banner(new StringBanner())
				//.bannerMode(Banner.Mode.OFF)
				.run(args);
	}

}
