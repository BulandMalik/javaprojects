package com.buland.springboot.ocr.springbootawstextractapp.configs;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public TextractClient textractClient() {

        return TextractClient.builder()
                .region(Region.US_EAST_1)
                //.credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .build();

        //return TextractClient.builder().build();
    }
}