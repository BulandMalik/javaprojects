package com.buland.springboot.emailfunction.services;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Address;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
@Qualifier("htmlEmailService")
@Slf4j
@RequiredArgsConstructor //@RequiredArgsConstructor is a Lombok annotation that generates constructors for all final and non-null fields. Non Null fields needs to be annotated with @NotNull
public class HTMLEmailService implements EmailService{

    private final JavaMailSender javaMailSender; //This dependency provides the necessary methods to send emails.
    private final TemplateEngine templateEngine;
    public static final String UTF_8_ENCODING = "UTF-8";


    /*public HTMLEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender =  javaMailSender;
    }*/

    public void sendEmail(String toEmail, String [] recipients, String subject, String emailBody){
        if (emailBody==null )
            emailBody = ""; //DO NOT DO IT, JUST for Demo Purpose!
        log.info("inside sendEmail with toEMail:{}, Subject:{}, emailBody:\n{}",toEmail, subject, emailBody);
        log.info("emailBody.contains(Using Spring Without MimeMessageHelper): {}",emailBody.contains("Using Spring Without MimeMessageHelper"));
        log.info("emailBody.contains(Using Spring With MimeMessageHelper): {}",emailBody.contains("Using Spring With MimeMessageHelper"));
        if (emailBody.equals("")) {
            //treat as Attachment
            //sendWelcomeHtmlEmail(toEmail, recipients, "Buland A Malik", subject, null, UUID.randomUUID().toString());
            sendHtmlEmailWithMultiPart(toEmail, recipients, "Buland Malik", subject, null, UUID.randomUUID().toString());
        } else if (emailBody.contains("Using Spring Without MimeMessageHelper")) {
            sendEmailWithoutMimeMessageHelper(toEmail, recipients, subject, emailBody);
        } else if (emailBody.contains("Using Spring With MimeMessageHelper")) {
            sendEmailWithMimeMessageHelper(toEmail, recipients, subject, emailBody);
        } else  {
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

            message.setPriority(1); //High priority Mail

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


    //@Async
    public void sendWelcomeHtmlEmail(String fromEmail, String [] recipients, String name, String subject, String emailBody, String token) {
        try {
            Context context = new Context();
            /*context.setVariable("name", name);
            context.setVariable("url", getVerificationUrl(host, token));*/
            //context.setVariables(Map.of("contents", name, "url", getVerificationUrl(host, token)));
            context.setVariables(Map.of("name", name, "url", getVerificationUrl("http://localhost:8080", token)));
            String text = templateEngine.process("welcome-email", context);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(subject);
            helper.setFrom(fromEmail);
            helper.setTo(recipients);
            helper.setText(text, true);
            //Add attachments (Optional)
            /*FileSystemResource fort = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/fort.jpg"));
            FileSystemResource dog = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/dog.jpg"));
            FileSystemResource homework = new FileSystemResource(new File(System.getProperty("user.home") + "/Downloads/images/homework.docx"));
            helper.addAttachment(fort.getFilename(), fort);
            helper.addAttachment(dog.getFilename(), dog);
            helper.addAttachment(homework.getFilename(), homework);*/
            javaMailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    public void sendHtmlEmailWithMultiPart(String fromEmail, String [] recipients, String name, String subject, String emailBody, String token) {
        final String TEXT_HTML_ENCONDING = "text/html";
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(subject);
            helper.setFrom(fromEmail);
            helper.setTo(recipients);

            Context context = new Context();
            context.setVariables(Map.of("name", name, "url", getVerificationUrl("http://localhost:8080/", token)));
            String text = templateEngine.process("welcome-email", context);

            //helper.setText(text, true);

            // Add HTML email body
            MimeMultipart mimeMultipart = new MimeMultipart("related"); //If a message in MIME format contains multiple related parts, the Content-Type parameter is set to Multipart/Related
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(text, TEXT_HTML_ENCONDING);
            mimeMultipart.addBodyPart(messageBodyPart);

            // Add images to the email body
            BodyPart imageBodyPart = new MimeBodyPart();
            //DataSource dataSource = new FileDataSource(System.getProperty("user.home") + "/img/spring-boot-email-logo.jpg"); //if file is not part of the project
            DataSource dataSource = new FileDataSource(new ClassPathResource("img/spring-boot-email-logo.jpg").getFile() );
            imageBodyPart.setDataHandler(new DataHandler(dataSource));
            imageBodyPart.setHeader("Content-ID", "image");
            mimeMultipart.addBodyPart(imageBodyPart);

            message.setContent(mimeMultipart); //add mimeMultipart to the MimeMessage

            javaMailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }

    }


    public static String getVerificationUrl(String host, String token) {
        return host + "/api/users?token=" + token;
    }
}
