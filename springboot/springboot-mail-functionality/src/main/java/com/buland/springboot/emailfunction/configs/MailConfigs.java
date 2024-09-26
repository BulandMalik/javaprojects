package com.buland.springboot.emailfunction.configs;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@Slf4j
@Getter
@Setter
public class MailConfigs {

    //@Value("${spring.mail.username}")
    private String userName;

    //@Value("${spring.mail.username}")
    private String password;

    private String host;

    private String port;

    private String fromAddress;

    private List<String> recipients;

    @PostConstruct
    public void printData(){
        log.info("<<<<<<<<<< Spring Mail SMTP Properties >>>>>>>>>>");
        log.info("From Email UserName: {}",userName);
        log.info("From Email Password: {}",password);
        log.info("SMTP Host: {}",host);
        log.info("SMTP Port: {}",port);
        log.info("From Email Address: {}",fromAddress);
        this.recipients.forEach(recipient -> log.info("Recipient: {}",recipient));
    }
}
