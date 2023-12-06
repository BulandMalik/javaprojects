package com.example.buland.cloud.aws.s3.imageuploaddownload.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import javax.annotation.PostConstruct;

@Configuration
@ConfigurationProperties("aws")
@Slf4j
public class AwsConfig {

    //@Value("${region}")
    @Value("${aws.region}")
    public String awsRegion = Region.US_EAST_1.toString();

    /**
     * Every object (file) in Amazon S3 must reside within a bucket. A bucket represents a collection (container) of objects.
     * Each bucket must have a unique key (name)
     */
    @Value("${aws.buckets.profile-media-bucket-name}")
    public String awsBucketName;

    ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create();

   @Bean
    public S3Client configureS3Client() { //AwsCredentialsProvider provider
       Region region = Region.of(awsRegion);
       //S3Client s3Client = S3Client.builder(); //thios should work as work as it looks default profile credentails
       /*
       S3Client s3Client = S3Client.builder()
               .region(region)
               .credentialsProvider(
                       StaticCredentialsProvider.create(
                               AwsBasicCredentials.create(
                                       awsId,
                                       awsSecret
                               )
                       )
               )
               .build();
        */

       S3Client s3Client = S3Client.builder()
               .region(region)
               .credentialsProvider(credentialsProvider)
               //.endpointOverride(URI.create("https://s3.us-west-2.amazonaws.com"))
               //.forcePathStyle(true) //Call the forcePathStyle method with true in your client builder to force the client to use path-style addressing for buckets.
               .build();
       return s3Client;
   }

    /**
     * You can use a S3Presigner object to sign an Amazon S3 SdkRequest so that it’s executed without requiring authentication
     * on the part of the caller.
     *
     * For example, assume Alice has access to an S3 object, and she wants to temporarily share access to that object with Bob.
     * Alice can generate a presigned GetObjectRequest object to share with Bob so that he can download the object without
     * requiring access to Alice’s credentials. You can generate presigned URLs for HTTP GET and for HTTP PUT requests.
     *
     * @see <a href="https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/examples-s3-presign.html">Work with Amazon S3 presigned URLs</a>
     *
     *
     * @return
     */
    @Bean
    public S3Presigner s3Presigner() { //AwsCredentialsProvider provider
        Region region = Region.of(awsRegion);
        return S3Presigner.builder()
                .region(region)
                .credentialsProvider(credentialsProvider)
                //.credentialsProvider(provider)
                .build();
    }

    @PostConstruct
    private void print() {
        log.info("AWS Region={}",awsRegion);
        log.info("AWS Bucket Name={}",awsBucketName);
    }

}
