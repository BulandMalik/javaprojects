package com.buland.springbootsqssnsswsdemo.resumeparser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Service
@Slf4j
public class SQSMessages {

    private SqsClient sqsClient;

    @Autowired
    private SQSConfig sqsConfig;

    /**
     * As of Spring Framework 4.3, an @Autowired annotation on such a constructor is no longer necessary if the target bean
     * defines only one constructor to begin with. However, if several constructors are available and there is no primary/default
     * constructor, at least one of the constructors must be annotated with @Autowired in order to instruct the container which one
     * to use.
     *
     * @param sqsConfig
     */
    @Autowired
    public SQSMessages(SQSConfig sqsConfig) {
        sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .build();
        sqsConfig = this.sqsConfig;
    }

    public SQSMessages(SqsClient sqsClient, SQSConfig sqsConfig) {
        sqsClient = this.sqsClient;
        sqsConfig = this.sqsConfig;
    }

    public void sendMessage(String message) {

        log.info("Send Single message");
        try {

            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl(sqsConfig.getResumeParserQueueURL())
                    //.messageBody("Hello from msg 1")
                    .messageBody(message)
                    .delaySeconds(5)
                    //.messageAttributes()
                    .messageGroupId("")
                    .build();
            sqsClient.sendMessage(sendMessageRequest);

        } catch (SqsException e) {
            log.error(e.awsErrorDetails().errorMessage());
        }
    }


    /**
     * receive all pending messages and delete them after wokring on those messages.
     */
    public void receiveMessages() {
        log.info("Receive messages");
        try {
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(sqsConfig.getResumeParserQueueURL())
                    .maxNumberOfMessages(2)
                    .build();
            sqsClient.receiveMessage(receiveMessageRequest).messages().forEach( message -> {
                log.info("Mid={}, \n MAttributes={}, \n ReceiptHandle={}, \n Body={}",
                        message.messageId(), message.messageAttributes(), message.receiptHandle(), message.body());
                deleteMessages(message);
            });

        } catch (SqsException e) {
            log.error(e.awsErrorDetails().errorMessage());
        }
    }


    /**
     * mimic the scenario where message will be received but will be thrown
     */
    public void receiveMessagesAndThrowError() {
        log.info("receiveMessagesAndThrowError....");
        try {
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(sqsConfig.getResumeParserQueueURL())
                    .maxNumberOfMessages(2)
                    .build();
            sqsClient.receiveMessage(receiveMessageRequest).messages().forEach( message -> {
                log.info("Mid={}, \n MAttributes={}, \n ReceiptHandle={}, \n Body={}",
                        message.messageId(), message.messageAttributes(), message.receiptHandle(), message.body());
                //throw new Exception("");
                //not deleting message will eventually move them to dlq
            });

        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * delete given messages.
     *
     * As per AWS docs, Messages are not automatically deleted after retrieval because Amazon SQS ensures that you do not lose access
     * to a message due to processing failures, such as issues with your application or network disruptions. To permanently remove a
     * message from the queue, you must explicitly send a delete request after processing the message to confirm successful receipt
     * and handling.
     *
     * When messages are retrieved via the Amazon SQS console, they are immediately made visible again for re-retrieval. This default
     * behavior ensures messages are not inadvertently lost during manual operations but can lead to repeated processing. In automated
     * environments, adjust the visibility timeout setting to control how long a message remains invisible to other consumers after being
     * retrieved. This setting is crucial for coordinating message processing across multiple consumers and ensuring that messages are
     * processed only once.
     *
     * For more info @lookup(https://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/step-receive-delete-message.html)
     *
     * @param message
     *
     * @return
     */
    public Boolean deleteMessages(Message message) {
        log.info("Deleting Message with id={}",message.messageId());

        try {
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(sqsConfig.getResumeParserQueueURL())
                    .receiptHandle(message.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteMessageRequest);
            return Boolean.TRUE;
        } catch (SqsException e) {
            log.error(e.awsErrorDetails().errorMessage());
        }
        return Boolean.FALSE;
    }

}