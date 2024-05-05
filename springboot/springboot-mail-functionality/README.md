##Spring Boot All With Mailing Functionality

## Using Spring CLI to Create the Project
    1. `spring shell` --RUn commnds oin the sheel without using spring
    2. spring init --type=maven-project -d=mail
    3. spring init list --list all options to be used during initialize phase

## Add Spring Boot Mail Starter Dependency
We need following spring boot dependency for mail handling.
```maven
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

## How to send email in Java Spring Boot and SMTP?
1. Add Spring Boot Mail Starter Dependency
2. Set the following configs in application.yaml file (application.properties) to configure the SMTP server
```angular2html
spring.mail.host=smtp.dummy.com
spring.mail.port=25
spring.mail.username=username
spring.mail.password=password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
3. Above config enable the SMTP authentication that is include `mail.smtp.auth` to enable SMTP authentication, which is necessary for the application to connect to the server, and mail.smtp.starttls.enable to enable the use of Transport Layer Security (TLS) to encrypt the connection.
4. Create a Service class that will send the email. Sample code is below.
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
```
5. If you want to send emails to multiple recipients than do following.
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String[] recipients, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        //message.setTo(new String[] {"recipient1@dummy.com", "recipient2@dummy.com", "recipient3@dummy.com"});
        message.setTo(recipients);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
```
6. Send Email either access a controller end point (API) or via another class (for example main class)
```java
String recipient = "john.doe@example.com";
String subject = "Hello, ${firstName}!";
String template = "Hello, ${firstName}!\n\n"
                  + "This is a message just for you, ${firstName} ${lastName}. "
                  + "We hope you're having a great day!\n\n"
                  + "Best regards,\n"
                  + "The Spring Boot Team";

Map<String, Object> variables = new HashMap<>();
variables.put("firstName", "John");
variables.put("lastName", "Doe");

sendEmail(recipient, subject, template, variables);
```
## How to Run the Application
Make sure you do pass following two JVM params.
1. -DE_USERNAME=[Your Email Id To Configure SMTP Server To Send Emails]
2. -DE_PASSWORD=[Your Password]

## How to send HTML emails in Spring Boot?

## How to send Emails with Attachments?