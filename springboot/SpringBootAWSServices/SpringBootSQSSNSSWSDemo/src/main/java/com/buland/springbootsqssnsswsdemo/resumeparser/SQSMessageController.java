package com.buland.springbootsqssnsswsdemo.resumeparser;


import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping(path="/sqs/messages")
@RequestMapping("/sqs/messages")
@Slf4j
public class SQSMessageController {

    private SQSMessages sqsMessages;

    public SQSMessageController(SQSMessages sqsMessages) {
        this.sqsMessages = sqsMessages;
    }

    @GetMapping("/")
    //@ResponseBody
    public String getAllMessages() {
        sqsMessages.receiveMessages();
        return "Done!!!";
    }

    @GetMapping("/dlq")
    //@ResponseBody
    public String testDLQ() {
        sqsMessages.receiveMessagesAndThrowError();
        return "Done!!!";
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(String message) { //@RequestBody Person person) {
        sqsMessages.sendMessage(message);
    }

}
