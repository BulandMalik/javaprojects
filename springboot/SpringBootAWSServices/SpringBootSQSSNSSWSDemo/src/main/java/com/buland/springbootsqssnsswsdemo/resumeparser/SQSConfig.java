package com.buland.springbootsqssnsswsdemo.resumeparser;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "sqs")
@Data
public class SQSConfig {

    private String resumeParserQueueURL;

    private String resumeParserDlqQueueURL;
}
