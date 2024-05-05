package com.buland.springboot.emailfunction.services;

import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

@Service
@Qualifier("htmlEmailService")
@Slf4j
public class HTMLEmailService implements EmailService{

    private JavaMailSender javaMailSender; //This dependency provides the necessary methods to send emails.

    public HTMLEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender =  javaMailSender;
    }

    public void sendEmail(String toEmail, String [] recipients, String subject, String emailBody){
        log.info("inside sendEmail with toEMail:{}, Subject:{}, emailBody:\n{}",toEmail, subject, emailBody);
        log.info("emailBody.contains(Using Spring Without MimeMessageHelper): {}",emailBody.contains("Using Spring Without MimeMessageHelper"));
        log.info("emailBody.contains(Using Spring With MimeMessageHelper): {}",emailBody.contains("Using Spring With MimeMessageHelper"));
        if (emailBody.contains("Using Spring Without MimeMessageHelper")) {
            sendEmailWithoutMimeMessageHelper(toEmail, recipients, subject, emailBody);
        } else if (emailBody.contains("Using Spring With MimeMessageHelper")) {
            sendEmailWithMimeMessageHelper(toEmail, recipients, subject, emailBody);
        } else {
            //treat as Attachment
            sendEmailWithAttachments(toEmail, recipients, subject, emailBody);
        }
    }

    public void sendEmailWithoutMimeMessageHelper(String fromEmail, String [] recipients, String subject, String emailBody){
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(new InternetAddress(fromEmail, "Spring Boot Email Demo App"));
            /*Arrays.stream(recipients).forEach( recipient -> {
            });*/
            InternetAddress[] recipientAddress = new InternetAddress[recipients.length];
            int counter = 0;
            for (String recipient : recipients) {
                recipientAddress[counter] = new InternetAddress(recipient.trim());
                counter++;
            }
            message.setRecipients(MimeMessage.RecipientType.TO, recipientAddress);
            message.setSubject(subject);

            message.setContent(emailBody, "text/html; charset=utf-8");

            javaMailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmailWithMimeMessageHelper(String fromEmail, String [] recipients, String subject, String emailBody){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            message.setFrom(fromEmail);

            message.setTo(recipients);
            message.setSubject(subject);

            //message.setText("my text <img src='cid:myLogo'>", true);
            message.setText(emailBody, true);
            message.addInline("myLogo", new ClassPathResource("img/spring-boot-email-logo.jpg"));

            //message.addAttachment("myDocument.pdf", new ClassPathResource("doc/myDocument.pdf"));

            javaMailSender.send(message.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendEmailWithAttachments(String fromEmail, String [] recipients, String subject, String emailBody){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            message.setFrom(fromEmail);

            message.setTo(recipients);
            message.setSubject(subject);

            //message.setText("my text <img src='cid:myLogo'>", true);
            message.setText(emailBody, true);
            message.addInline("myLogo", new ClassPathResource("img/spring-boot-email-logo.jpg"));

            message.addAttachment("sample-1.pdf", new ClassPathResource("doc/sample-1.pdf"));
            message.addAttachment("spring-boot-email-logo.jpg", new ClassPathResource("img/spring-boot-email-logo.jpg"));

            javaMailSender.send(message.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
