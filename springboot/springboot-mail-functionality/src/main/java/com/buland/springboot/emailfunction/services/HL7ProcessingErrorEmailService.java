package com.buland.springboot.emailfunction.services;

import com.buland.springboot.emailfunction.model.MyError;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.util.*;

@Service
@Qualifier("hl7ProcessingErrorEmailService")
@Slf4j
@RequiredArgsConstructor //@RequiredArgsConstructor is a Lombok annotation that generates constructors for all final and non-null fields. Non Null fields needs to be annotated with @NotNull
public class HL7ProcessingErrorEmailService implements EmailService {

    private final JavaMailSender javaMailSender; //This dependency provides the necessary methods to send emails.
    private final TemplateEngine templateEngine;
    public static final String UTF_8_ENCODING = "UTF-8";

    @Override
    public void sendEmail(String fromEmail, String[] recipients, String emailSubject, String name) {
            final String TEXT_HTML_ENCONDING = "text/html";
            try {

                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
                helper.setPriority(1);
                helper.setSubject(emailSubject);
                helper.setFrom(fromEmail);
                helper.setTo(recipients);

                Context ctx = new Context(Locale.US);
                ctx.setVariable("pageTitle", emailSubject);
                ctx.setVariable("reportDate", LocalDate.now());

                List<MyError> errors = createErrors();
                ctx.setVariable("errors", errors); //Map of String(Country) & Decimal (Sale Amount)

                String text = templateEngine.process("hl7v2-processing-error-details", ctx);

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
                imageBodyPart.setHeader("Content-ID", "imageCompanyLogo");
                mimeMultipart.addBodyPart(imageBodyPart);

                message.setContent(mimeMultipart); //add mimeMultipart to the MimeMessage

                javaMailSender.send(message);
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                throw new RuntimeException(exception.getMessage());
            }
    }

    private List<MyError> createErrors() {
        List<MyError> errors = new ArrayList<MyError>(5);
        for (int i=0; i<5; i++) {
            MyError e = MyError.builder().id(i).unparssedMessageId(i).errorNumber("HL7-00"+i).errorDetails("Cannot process this message - "+i).tid(UUID.randomUUID().toString()).link("http://localhost:8080/errors/"+i).build();
            errors.add(e);
        }
        return errors;
    }
}
