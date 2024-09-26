package com.buland.springbootsqssnsswsdemo.examples;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SqsException;
import software.amazon.awssdk.services.sqs.paginators.ListQueuesIterable;

/**
 * Before running this Java V2 code example, set up your development
 * environment, including your credentials.
 *
 * For more information, see the following documentation topic:
 *
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
//@Slf4j
public class HelloSQS {

    public static void main(String[] args) {

        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .build();

        listQueues(sqsClient);
        sqsClient.close();
    }

    public static void listQueues(SqsClient sqsClient) {
        try {
            ListQueuesIterable listQueues = sqsClient.listQueuesPaginator();
            listQueues.stream()
                    .flatMap(r -> r.queueUrls().stream())
                    .forEach(content -> System.out.println(" Queue URL: " + content.toLowerCase()));

        } catch (SqsException e) {
            //log.error(e.awsErrorDetails().errorMessage());
            System.out.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}