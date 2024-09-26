# Spring Boot Example with SQS, SNS & SWS

We are using AWS SDK for Java as a dependency to do integration with AWS services.

We can also use [Spring Cloud AWS](https://awspring.io/). An example of such project is [spring-cloud-aws-sns-sqs-localstack](https://github.com/visa2learn/spring-cloud-aws-sns-sqs-localstack/tree/master)

Look for broader AWS Samples [here](https://github.com/awsdocs/aws-doc-sdk-examples/tree/main/javav2/example_code/sqs#code-examples)

## Cloud Local testing using LocalStack
We will be using LocalStack for local testing mimic-ing AWS.

1. Installation on MacOS --> `brew install localstack/tap/localstack-cli`
2. Check Version --> `localstack --version`
3. Install awslocal --> `pip install awscli-local`
4. List ALL SQS Queues --> `awslocal sqs list-queues`
5. Create a Queue --> 
   - `awslocal sqs create-queue --queue-name my-queue`
   - `aws --endpoint-url=http://localhost:4566 sqs create-queue --queue-name sample-queue.fifo --attributes FifoQueue=true`
6. Retrieve the queue attributes --> `awslocal sqs get-queue-attributes --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-queue --attribute-names All`
7. Send Message
   - `awslocal sqs send-message --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-queue --message-body "Hello World!!!!"`
   - `aws sqs send-message --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-queue --message-body "Hello Worldawslocal sqs get-queue-attributes --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-queue --attribute-names Allawslocal sqs get-queue-attributes --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-queue --attribute-names All"`
8. Receive Message --> `awslocal sqs receive-message --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-queue`
9. Delete Message --> `awslocal sqs delete-message --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-queue --receipt-handle <receipt-handle>`
   - Replace `<receipt-handle>` with the receipt handle you received in the previous step.
   - During receive Message, we get the `ReceiptHandle`
10. Purge Queue --> `awslocal sqs purge-queue --queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/my-queue`

### Dead-letter queue testing
LocalStack’s SQS implementation supports both regular dead-letter queues (DLQ) and DLQ redrive via move message tasks. Here’s an end-to-end example of how to use message move tasks to test DLQ redrive.

First, create three queues. One will serve as original input queue, one as DLQ, and the third as target for DLQ redrive.

1. `awslocal sqs create-queue --queue-name input-queue`
2. `awslocal sqs create-queue --queue-name dead-letter-queue`
3. `awslocal sqs create-queue --queue-name recovery-queue`

Configure dead-letter-queue to be a DLQ for input-queue:
`awslocal sqs set-queue-attributes \
--queue-url http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/input-queue \
--attributes '{
"RedrivePolicy": "{\"deadLetterTargetArn\":\"arn:aws:sqs:us-east-1:000000000000:dead-letter-queue\",\"maxReceiveCount\":\"1\"}"
}'`

### SQS Query API

1. `curl "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/input-queue?Action=SendMessage&MessageBody=hello%2Fworld"`
2. To get JOSn response --> `curl -H "Accept: application/json" "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/localstack-queue?Action=SendMessage&MessageBody=hello%2Fworld"`
3. 

For more, look [LocalStack SQS Support](https://docs.localstack.cloud/user-guide/aws/sqs/)