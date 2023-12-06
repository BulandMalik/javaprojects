package com.example.buland.cloud.aws.s3.imageuploaddownload.utils;

import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

/**
 * Following code uses AWS SDK and specifically using S3 API to put an object into a S3 bucket, with object’s data is read from an
 * InputStream object. Note that you must specify a bucket name that should exists in your AWS account.
 */
@Slf4j
public class S3Util {
    private static final String BUCKET = "test-img-handling";

    /**
     * By default, the file uploaded to a bucket has read-write permission for object owner. It is not accessible for public users (everyone).
     * If you want to give public-read access for public users, use the acl() method as below:
     *
     * @param fileName
     * @param inputStream
     * @throws S3Exception
     * @throws AwsServiceException
     * @throws SdkClientException
     * @throws IOException
     */
    public static void uploadFileWithoutPublicAccess(String id, String fileName, InputStream inputStream)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        S3Client client = S3Client.builder().build(); //by default uses access key id & secrets from aws credentials file
         
        PutObjectRequest request = PutObjectRequest.builder()
                            .bucket(BUCKET )
                            .key(id + "/" + fileName)
                            .build();

        //the method returns immediately as the put object operation is executed asynchronously.
        client.putObject(request,
                RequestBody.fromInputStream(inputStream, inputStream.available()));
    }

    public static void uploadFileWithPublicAccess(String fileName, InputStream inputStream)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        S3Client client = S3Client.builder().build();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .acl("public-read") //public read access for everyone, you might get The bucket does not allow ACLs if bucket does not allow it
                .build();

        //the method returns immediately as the put object operation is executed asynchronously.
        client.putObject(request,
                RequestBody.fromInputStream(inputStream, inputStream.available()));
    }

    public static void asyncUploadFileWithPublicAccess(String fileName, InputStream inputStream)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        S3Client client = S3Client.builder().build();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .acl("public-read") //public read access for everyone
                .build();

        client.putObject(request,
                RequestBody.fromInputStream(inputStream, inputStream.available()));

        // In case you want to run some custom logics that depend on the existence of the uploaded file,
        // add the following code that waits until the file exists on S3 and runs the custom logic.
        S3Waiter waiter = client.waiter();
        HeadObjectRequest waitRequest = HeadObjectRequest.builder()
                .bucket(BUCKET)
                .key(fileName)
                .build();

        WaiterResponse<HeadObjectResponse> waitResponse = waiter.waitUntilObjectExists(waitRequest);

        waitResponse.matched().response().ifPresent(x -> {
            log.info("S3 operation response content type", x.contentType());
        });
    }

    public static byte[] getFile(String key) throws S3Exception, AwsServiceException,
            SdkClientException, IOException {
        try{
            S3Client client = S3Client.builder().build();

            log.info("inside getFile with key={}",key);
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(key)
                    .build();

            //ResponseInputStream<GetObjectResponse> getObjectRes = client.getObject(getObjectRequest);
            //log.info("S3 Response Object contentType:{}",getObjectRes.response().contentType());
            //log.info("S3 Response Object contentType:{}",getObjectRes.response());
            ResponseBytes<GetObjectResponse> objectBytes = client.getObjectAsBytes(getObjectRequest);
            byte[] data = objectBytes.asByteArray();

            /*
            // Write the data to a local file.
            File myFile = new File(path );
            OutputStream os = new FileOutputStream(myFile);
            os.write(data);
            System.out.println("Successfully obtained bytes from an S3 object");
            os.close();
            */
            return data;
        } catch (S3Exception e) {
            log.error(e.awsErrorDetails().errorMessage());
            //System.exit(1);
        }
        return null;
    }

    public byte[] getObject(String bucketName, String key) {

        S3Client client = S3Client.builder().build();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> res = client.getObject(getObjectRequest);

        try {
            return res.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}