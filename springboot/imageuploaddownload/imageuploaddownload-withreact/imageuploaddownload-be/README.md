# Image Upload and Download Examples Using Spring Boot, React and AWS S3

## Dependencies/Tools Used in the Tutorial
1. Spring Boot Framework to build the backend
2. React Framework to build the SPA (Single Page Application)
3. AWS SDK for S3 objects handling
   1. https://aws.amazon.com/sdk-for-java/
   2. https://github.com/aws/aws-sdk-java-v2/#using-the-sdk
```aidl
<dependency>
  <groupId>software.amazon.awssdk</groupId>
  <artifactId>aws-sdk-java</artifactId>
  <version>2.21.36</version>
</dependency>
```
4. AWS Account (Free or Business)
   1. https://aws.amazon.com/
5. 

## Setup AWS Account
1. If you do not have one, than setup for AWS Free Tier account
2. If you already have one, login to AWS console

## AWS SDK for Java for Amazon S3 Development
You need to make sure that amazon cli has been setup properly on your local machine to do any sort of development with AWS services or use its AWS SDK.
1. [Setup AWS CLI](https://docs.aws.amazon.com/cli/latest/userguide/getting-started-quickstart.html)
   1. it actually creates `~/.aws/credentials` file that contains access id & secret and at times session key as well.
2. Create IAM Credentials to be used

## How to Generate AWS Access Key ID and Secret Access Key
One of the way to use AWS services programmatically is to create Amazon Web Services (AWS) credentials that allow applications to use AWS services programmatically (programmatic access). In our case this fits well as we need to use AWS S3 service API's programmatically to uploading/download files From AWS S3 buckets.

You must [sign up for an AWS account](https://www.youtube.com/watch?v=kfxhfAq8PNY) and [create an IAM admin user](https://www.youtube.com/watch?v=pPUjYD5nY_k) beforehand. You can use your AWS account root user but it is not recommended.

Follow [How to Create AWS Access Key ID and Secret Access Key](https://www.youtube.com/watch?v=yysled3Ir1o&t=2s)

## Setup Project
1. Open Project in the IDE's (IntelliJ, Visual Studio Code, Eclipse etc.)
2. Create AWS IAM AccessKeys that allows the application to access S3 and upload/download images.
   1. Go to your AWS Account -> Security Credentials

