package com.buland.springboot.emailfunction.controllers;

import com.buland.springboot.emailfunction.configs.MailConfigs;
import com.buland.springboot.emailfunction.services.EmailService;
import com.buland.springboot.emailfunction.services.TextEmailService;
import org.eclipse.angus.mail.iap.Response;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@Controller //This is simply a specialization of the @Component class, which allows us to auto-detect implementation classes through the classpath scanning.
@RestController //@RestController is a specialized version of the controller. It includes the @Controller and @ResponseBody annotations, and as a result, simplifies the controller implementation:
@RequestMapping("/email") //We typically use @Controller in combination with a @RequestMapping annotation for request handling methods.
public class EmailController {


    EmailService textEmailService;

    EmailService htmlEmailService;

    EmailService salesReportEmailService;

    EmailService hl7ErrorsEmailService;

    private MailConfigs mailConfigs;

    public EmailController( @Qualifier("textEmailService") EmailService textEmailService,
                            @Qualifier("htmlEmailService") EmailService htmlEmailService,
                            @Qualifier("salesReportEmailService") EmailService salesReportEmailService,
                            @Qualifier("hl7ProcessingErrorEmailService") EmailService hl7ErrorsEmailService,
                            MailConfigs mailConfigs) {
        this.textEmailService = textEmailService;
        this.htmlEmailService = htmlEmailService;
        this.salesReportEmailService = salesReportEmailService;
        this.hl7ErrorsEmailService = hl7ErrorsEmailService;
        this.mailConfigs = mailConfigs;
    }

    //The controller is annotated with the @RestController annotation; therefore, the @ResponseBody isn’t required.
    //@GetMapping("/hello")
    @RequestMapping(value="/helloText", method= RequestMethod.POST)
    public String sendHelloTextEmail()
    {
        String [] recipients = {"dummy@gmail.com"};
        String firstName = "Paul", lastName = "Adams";
        String subject = String.format("Hello, %s!", firstName);
        String template = String.format("Hello, %s!\n\n"
                + "This is a message test message for %s, %s. "
                + "We hope you're having a great day!\n\n"
                + "Best regards,\n"
                + "Email Demo Application Inc.",firstName, firstName, lastName);

        textEmailService.sendEmail(mailConfigs.getUserName(), recipients, subject, template);
        return "All Good!!!";
    }

    @RequestMapping(value="/helloHtml1", method= RequestMethod.POST)
    public String sendHelloHtmlEmail1()
    {
        String [] recipients = {"dummy@gmail.com"};
        String firstName = "Paul", lastName = "Adams";
        String subject = String.format("Hello, %s from SpringBoot HTML Email Template!", firstName);
        String htmlTemplate = "<h1>Hello ${name}<h1>." +
                "<h1>Using Spring Without MimeMessageHelper<h1>." +
                "<p><h2>This is a test Spring Boot email</h2></p>" +
                "<p>${message}</p>";

        // Replace placeholders in the HTML template with dynamic values
        htmlTemplate = htmlTemplate.replace("${name}", firstName);
        htmlTemplate = htmlTemplate.replace("${message}", "Hello, this is a test email.");

        htmlEmailService.sendEmail(mailConfigs.getUserName(), recipients, subject, htmlTemplate);
        return "HTML Looks Good!!!";
    }

    @RequestMapping(value="/helloHtml2", method= RequestMethod.POST)
    public String sendHelloHtmlEmail2()
    {
        String [] recipients = {"dummy@gmail.com"};
        String firstName = "Paul", lastName = "Adams";
        String subject = String.format("Hello, %s from SpringBoot HTML Email Template!", firstName);
        String htmlTemplate = "<h1>Hello ${name}<h1>." +
                "<h1>Using Spring With MimeMessageHelper<h1>." +
                "<p>My Logo: <img src='cid:myLogo'></p>" +
                "<p>${message}</p>";

        // Replace placeholders in the HTML template with dynamic values
        htmlTemplate = htmlTemplate.replace("${name}", firstName);
        htmlTemplate = htmlTemplate.replace("${message}", "Hello, this is a test email.");

        htmlEmailService.sendEmail(mailConfigs.getUserName(), recipients, subject, htmlTemplate);
        return "HTML Looks Good!!!";
    }

    @RequestMapping(value="/helloHtmlWithAttachment", method= RequestMethod.POST)
    public String sendHelloHtmlEmailWithAttachment()
    {
        String [] recipients = {"dummy@gmail.com"};
        String firstName = "Paul", lastName = "Adams";
        String subject = String.format("Hello, %s from SpringBoot HTML Email Template!", firstName);
        String htmlTemplate = "<h1>Hello ${name}<h1>." +
                "<h1>Spring Email With Attachment<h1>." +
                "<p><img src='cid:myLogo'></p>";

        // Replace placeholders in the HTML template with dynamic values
        htmlTemplate = htmlTemplate.replace("${name}", firstName);

        htmlEmailService.sendEmail(mailConfigs.getUserName(), recipients, subject, htmlTemplate);
        return "HTML Attachment Looks Good!!!";
    }

    @RequestMapping(value="/helloHtmlUsingWelcomeTemplate", method= RequestMethod.POST)
    public String sendHelloHtmlEmailUsingWelcomeTemplate()
    {
        String [] recipients = {"dummy@gmail.com"};
        String subject = "Hello from SpringBoot HTML Welcome Email Template!";


        htmlEmailService.sendEmail(mailConfigs.getUserName(), recipients, subject, null);
        return "HTML With template Looks Good!!!";
    }

    @RequestMapping(value="/salesReport", method= RequestMethod.POST)
    public String sendSalesreportEmail()
    {
        String [] recipients = {"dummy@gmail.com"};
        String subject = "Sales Report - "+new Date();


        salesReportEmailService.sendEmail(mailConfigs.getUserName(), recipients, subject, "Joe F Donald");
        return "Sales Report Looks Good!!!";
    }

    @RequestMapping(value="/hl7Errors", method= RequestMethod.POST)
    public String sendHL7ErrorsEmail()
    {
        String [] recipients = {"dummy@gmail.com"};
        String subject = "HL7 Errors Report - "+new Date();


        hl7ErrorsEmailService.sendEmail(mailConfigs.getUserName(), recipients, subject, "Joe F Donald");
        return "HL7 Errors Report Went Out!!!";
    }
}
