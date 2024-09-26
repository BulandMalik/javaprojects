package com.buland.springboot.emailfunction.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


public interface EmailService {

    public void sendEmail(String fromEmail, String [] recipients, String emailSubject, String emailBody);
}
