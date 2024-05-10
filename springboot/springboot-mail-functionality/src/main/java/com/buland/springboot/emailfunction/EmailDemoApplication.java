package com.buland.springboot.emailfunction;


import com.buland.springboot.emailfunction.configs.MailConfigs;
import com.buland.springboot.emailfunction.services.EmailService;
import com.buland.springboot.emailfunction.services.TextEmailService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
@Configuration
public class EmailDemoApplication {

    @Autowired
    @Qualifier("textEmailService")
    EmailService textEmailService;

    @Autowired
    private MailConfigs mailConfigs;
    public static void main(String[] args) {
        SpringApplication.run(com.buland.springboot.emailfunction.EmailDemoApplication.class, args);
    }

    /**
     * By utilizing the @EventListener(ApplicationReadyEvent.class) annotation, you ensure that the email is sent
     * automatically when the application is fully started and ready to handle requests.
     */
    @EventListener(ApplicationReadyEvent.class) //it will be triggered when the application is ready to handle requests.
    public void sendMail(){
        //String [] recipients = {"bulandmalik.learnings@gmail.com"};//bulandmalik.learnings@gmail.com
        String [] recipients = mailConfigs.getRecipients().toArray(new String[0]);
        //mailConfigs.getRecipients().toArray(recipients);
        textEmailService.sendEmail(mailConfigs.getFromAddress(), recipients,"Application Ready to Send Emails!!!","Just a notification....");
    }


}