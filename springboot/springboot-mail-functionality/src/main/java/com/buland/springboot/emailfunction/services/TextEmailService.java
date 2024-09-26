package com.buland.springboot.emailfunction.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Qualifier("textEmailService")
public class TextEmailService implements EmailService{

    private JavaMailSender javaMailSender; //This dependency provides the necessary methods to send emails.

    public TextEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender =  javaMailSender;
    }

    public void sendEmail(String fromEmail, String [] recipients, String emailSubject, String emailBody){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(recipients);
        message.setSubject(emailSubject);
        message.setText(emailBody);

        javaMailSender.send(message);
    }
}
