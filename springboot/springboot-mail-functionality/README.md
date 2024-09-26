##Spring Boot All With Mailing Functionality

## Basic Mail Terminologies
Email is a fairly old school technology meant to replicate postal service digitally. To understand how email works, we must know about network protocols that specify sets of data formats and data exchange rules for exchanging messages over the network. In the modern internet email sending part is conceptually (and sometimes technically) separate from receiving part. Email sending aspects are formalised in RFCs that describe SMTP protocol, whereas reception part can be done either via IMAP or POP3 protocol. The conceptual difference between IMAP and POP3 is that IMAP is meant to provide a way to browse mailboxes via network connection whereas POP3 is meant to download received messages into user-controlled computer and let them read it offline without any persistent connection to the server. Furthermore, MIME data format is used for encoding contents and inner structure of the messages that are transferred.

Broadly speaking, email system is federated and consists of the following parts:

1. `Mail Transfer Agent (MTA)` is a program that implements SMTP protocol to transport messages between hosts (e.g. Sendmail, qmail, Postfix).
2. `Mail User Agent (MUA)` is the email client application (e.g. mutt, Mail.app, Outlook, Thunderbird).
3. There can also be a `Mail Delivery Agent (MDA)` - an intermediate piece of software that bridges the gap between MTA and MUA on the receiving side (e.g. procmail). This can be done for spam filtering purposes and to manage the email message persistence on Unix/Linux systems.
4. Lastly, there can also be a `Mail Submission Agent (MSA)` that is equivalent of MDA, but for sending the email.

The general big-picture flow of email message is the following:

1. A sender creates the email message and sends it from MUA to MTA managed by their email provider (possibly via MSA). SMTP protocol is being used here.
2. Message traverses one or more MTAs. Again, this is done via SMTP protocol.
3. Message reaches the final MTA belonging to the email provider of the recipient and is stored there.
4. Recipient uses MUA on their end to retrieve the message (possibly via MDA). Either IMAP or POP3 protocol can be used here.

## Using Spring CLI to Create the Project
    1. `spring shell` --RUn commnds oin the sheel without using spring
    2. spring init --type=maven-project -d=mail
    3. spring init list --list all options to be used during initialize phase

## What is Spring Mail?
The Spring Framework provides a helpful utility library for sending email that shields the user from the specifics of the underlying mailing system and is responsible for low level resource handling on behalf of the client.

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
spring.mail.password=your-app-password-from-previous-step (Without spaces or any special character)
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
3. In particular, certain default timeout values are infinite, and you may want to change that to avoid having a thread blocked by an unresponsive mail server, as shown in the following example
```properties
spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=3000
spring.mail.properties.mail.smtp.writetimeout=5000
```
4. Above config enable the SMTP authentication that is include `mail.smtp.auth` to enable SMTP authentication, which is necessary for the application to connect to the server, and mail.smtp.starttls.enable to enable the use of Transport Layer Security (TLS) to encrypt the connection.
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

## Simplifying Email Templating with Thymeleaf and Spring Boot

### What is Thymeleaf?
1. `Thymeleaf` is a modern server-side Java template engine for both web and standalone environments. Thymeleaf’s main goal is to bring elegant natural templates to your development workflow — HTML that can be correctly displayed in browsers and also work as static prototypes, allowing for stronger collaboration in development teams.
2. `Thymeleaf` is a java template engine, Commonly used to generate the HTML views for the web. 
3. It is a general-purpose template engine which is capable of both web and standalone environment to generate dynamic views. 
4. It is a separate project, unrelated to spring we can create views without the use of spring.

### Where can we use Thymeleaf?
Thymeleaf can be used for both web and non-web applications.
1. Web Views 
2. PDF Templates 
3. Email Templates 
4. CSV Templates 
5. API Documentation 
6. Many more use cases

### Why do we need Thymeleaf for Mailing?
1. `Templating`: Thymeleaf provides a flexible way to create HTML templates with placeholders for dynamic data. This enables you to create consistent and visually appealing email layouts that can include images, styles, and other HTML elements. 
2. `HTML Escaping`: Thymeleaf automatically escapes HTML content, helping to prevent cross-site scripting (XSS) vulnerabilities when injecting dynamic content into email templates. 
3. `Ease of Use`: Thymeleaf uses familiar HTML syntax, which makes it approachable for developers who are already comfortable with web development technologies. 
4. `Dynamic Content`: Thymeleaf allows you to easily inject dynamic content into email templates. 
5. `Fragment Reuse`: Thymeleaf lets you define reusable fragments or components that you can include in multiple templates.

### Thymeleaf template
1. A thymeleaf template can be a combination of HTML tags with some thymeleaf expressions. 
2. It includes dynamic content to an HTML page with the help of thymeleaf expressions. 
3. It can access java code, objects, spring beans, and so on.

### Spring Boot Thymeleaf Dependency
```maven
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### Templates as Classpath Resources
> By default, Spring boot will pick Thymeleaf templates (HTML pages) from the `resources/templates` folder.

Template files can be shipped within the JAR file, which is the simplest way to maintain cohesion between templates and their input data.
To locate templates from the JAR, we use the ClassLoaderTemplateResolver. For example, if you templates are in the main/resources/templates/mail directory, so we set the Prefix attribute relative to the resource directory:
```java
@Bean
public ITemplateResolver thymeleafTemplateResolver() {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML");
    templateResolver.setCharacterEncoding("UTF-8");
    return templateResolver;
}
```
### Templates From External Sources/Directory
In other cases, we may want to modify templates without having to rebuild and deploy. To achieve this, we can put the templates on the filesystem instead.
It might be useful to configure this path in application.properties so that we can modify it for each deployment. This property can be accessed using the @Value annotation:
```java
@Value("${spring.mail.templates.path}")
private String mailTemplatesPath;
```
We then pass this value to a FileTemplateResolver, in place of the ClassLoaderTemplateResolver in our thymeleafTemplateResolver method:
```java
FileTemplateResolver templateResolver = new FileTemplateResolver();
templateResolver.setPrefix(mailTemplatesPath);
```

### Configure the Thymeleaf Engine
Finally, we need to create the factory method for the Thymeleaf engine. We’ll need to tell the engine which TemplateResolver we’ve chosen, which we can inject via a parameter to the bean factory method:

```java

@Bean
public SpringTemplateEngine thymeleafTemplateEngine(ITemplateResolver templateResolver) {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    templateEngine.setTemplateEngineMessageSource(emailMessageSource());
    return templateEngine;
}

@Bean
public ResourceBundleMessageSource emailMessageSource() {
    final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("mailMessages");
    return messageSource;
}
```
### Localization with Thymeleaf
In order to manage translations with Thymeleaf, we can specify a MessageSource instance to the engine:
```java
@Bean
public ResourceBundleMessageSource emailMessageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("mail-messages");
    return messageSource;
}

@Bean
public SpringTemplateEngine thymeleafTemplateEngine() {
   ...
   templateEngine.setTemplateEngineMessageSource(emailMessageSource());
   ...
}
```
Then, we'll create resource bundles for each locale we support: `src/main/resources/mail-messages_xx_YY.properties`. For example we can hve following structure.
1. src/main/resources/mail-messages.properties
2. src/main/resources/mail-messages_en_US.properties
3. src/main/resources/mail-messages_fr_FR.properties

## Thymeleaf Templates Content
For example, we can have following html file `mail-template-thymeleaf.htm` with placeholder that will be filled using template engines. We do this using the Thymeleaf notation, that is, ${…} for variables and #{…} for localized strings.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  </head>
  <body>
    <p th:text="#{greetings(${recipientName})}"></p>
    <p th:text="${text}"></p>
    <p th:text="#{regards}"></p>
    <p>
      <em th:text="#{signature(${senderName})}"></em> <br />
    </p>
  </body>
</html>
```
As the template engine is correctly configured, it’s very simple to use it: We’ll just create a Context object that contains template variables (passed as a Map here).

```java
@Autowired
private SpringTemplateEngine thymeleafTemplateEngine;

@Override
public void sendMessageUsingThymeleafTemplate(
    String to, String subject, Map<String, Object> templateModel)
        throws MessagingException {
                
    Context thymeleafContext = new Context();
    thymeleafContext.setVariables(templateModel);
    String htmlBody = thymeleafTemplateEngine.process("mail-template-thymeleaf.html", thymeleafContext);
    
    sendMail(to, subject, htmlBody);
}
```

### Emails With Embedded Images
We can include in our html and fill those images dynamically using [CID attachment](https://mailtrap.io/blog/embedding-images-in-html-email-have-the-rules-changed/#CID_attachments_or_embedding_an_image_using_MIME_object).
1. We have to set MimeMessageHelper as multi-part by passing true to the second argument of the constructor:
`MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");`
2. Read the image file as a resource. It will be located under `src/main/resources`
```java
@Value("classpath:/logo.png")
Resource resourceFile;
```
3. Using the CID notation, image has to be referenced in either the Thymeleaf and/or FreeMarker templates. For example, `<img src="cid:my_attachment.png" />`
4. In the SendMail(...) method, we’ll add resourceFile as an inline attachment, to be able to reference it with CID: `helper.addInline("my_attachment.png", resourceFile)`

## Other template Engine Options
1. FreeMarker Configuration - We can use FreeMarker template resolver for FreeMarker templates (.ftl).

## Killing a running app in Windows
1. Look for running app on a particular port `netstat -aof | findstr :8080`
2. kill the task using `tskill 14508`

## Running Application
-Dspring.profiles.active=dev -DE_USERNAME= -DE_PASSWORD=.......

### Additional Info
1. https://medium.com/thymeleaf-basics-concepts/thymeleaf-1ef952db0740
2. https://www.baeldung.com/spring-email-templates
3. https://www.baeldung.com/spring-template-engines
4. https://docs.spring.io/spring-framework/reference/integration/email.html
5. https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties.mail
6. https://javaee.github.io/javamail/docs/api/com/sun/mail/smtp/package-summary.html
7. https://www.trickster.dev/post/smtp-imap-pop3-and-mime-understanding-email-protocols/
8. https://www.ibm.com/docs/en/b2badv-communication/1.0.0?topic=concepts-mime-multipart-message-overview
9. https://github.com/thymeleaf/thymeleaf-extras-java8time
10. https://www.baeldung.com/thymeleaf-iteration
