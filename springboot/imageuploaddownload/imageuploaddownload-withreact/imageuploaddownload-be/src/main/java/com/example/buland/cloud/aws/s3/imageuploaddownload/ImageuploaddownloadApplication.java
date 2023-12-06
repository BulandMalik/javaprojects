package com.example.buland.cloud.aws.s3.imageuploaddownload;

import com.example.buland.cloud.aws.s3.imageuploaddownload.entities.Profile;
import com.example.buland.cloud.aws.s3.imageuploaddownload.service.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
public class ImageuploaddownloadApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageuploaddownloadApplication.class, args);
	}

	@Autowired
	private ProfileService profileService;

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			log.info("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

			//Save Profiles
			Profile profile1 = Profile.builder()
					.profileId("1")
					.profileName("Buland Malik")
					.build();
			profileService.saveProfile(profile1);

			Profile profile2 = Profile.builder()
					.profileId("2")
					.profileName("Qasim Malik")
					.build();
			profileService.saveProfile(profile2);
		};
	}

}
