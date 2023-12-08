package com.example.buland.cloud.aws.s3.imageuploaddownload.service;

import com.example.buland.cloud.aws.s3.imageuploaddownload.configs.AwsConfig;
import com.example.buland.cloud.aws.s3.imageuploaddownload.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Map;

@Service
@Slf4j
public class S3Service {

    private final S3Client s3Client;
    private final AwsConfig awsConfig;
    private final S3Presigner s3Presigner;

    public S3Service(S3Client s3Client, AwsConfig awsConfig, S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.awsConfig = awsConfig;
        this.s3Presigner = s3Presigner;
    }

    /**
     * to put an object into S3 bucket
     *
     * @param key
     * @param inputStream
     * @return
     * @throws S3Exception
     * @throws AwsServiceException
     * @throws SdkClientException
     * @throws IOException
     * @throws IOException
     */
    public Boolean putObject(String key, Map<String, String> metaData, InputStream inputStream)
            throws S3Exception, AwsServiceException, SdkClientException, IOException, IOException {

        log.info("inside  putObject with params key={}, metaData.{}={} , metaData.{}={} and metaData.{}={}",key,
                Constants.DOCUMENT_CONTENT_TYPE_META_DATA_KEY, metaData.get(Constants.DOCUMENT_CONTENT_TYPE_META_DATA_KEY),
                Constants.DOCUMENT_CONTENT_LENGTH_META_DATA_KEY, metaData.get(Constants.DOCUMENT_CONTENT_LENGTH_META_DATA_KEY),
                Constants.DOCUMENT_CATEGORY_META_DATA_KEY, metaData.get(Constants.DOCUMENT_CATEGORY_META_DATA_KEY));

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(awsConfig.awsBucketName)
                .key(key)
                .contentType(metaData.get(Constants.DOCUMENT_CONTENT_TYPE_META_DATA_KEY))
                .contentLength(Long.parseLong(metaData.get(Constants.DOCUMENT_CONTENT_LENGTH_META_DATA_KEY)))
                .metadata(metaData)
                .build();

        PutObjectResponse response = s3Client.putObject(
                putObjectRequest,
                RequestBody.fromInputStream(inputStream, inputStream.available())
        );

        log.info("Successfully upload the document with response={}",response);
        return Boolean.TRUE;
    }


    /**
     * gets the PreSigned Get Url where the Url is only valid for 5 mins
     *
     * @param objectKey
     *
     * @return
     */
    public String getPreSignedGetUrl(String objectKey) {
        log.info("inside getPreSignedGetUrl with objectKey={}",objectKey);

        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(awsConfig.awsBucketName)
                    .key(objectKey)
                    .build();

            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(5)) //pre signed URL will be valid for 5 mins only and after 5 mins we get 'Request has expired' error from AWS
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(getObjectPresignRequest);
            log.info("Presigned URL: {}", presignedGetObjectRequest.url());
            return presignedGetObjectRequest.url().toString();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            //throw ex;
        }
        return null;
    }


    /**
     * remove an object from S3
     *
     * @param objectKey
     * @return
     * @throws S3Exception
     * @throws AwsServiceException
     * @throws SdkClientException
     * @throws IOException
     * @throws IOException
     */
    public Boolean removeObject(String objectKey)
            throws S3Exception, AwsServiceException, SdkClientException, IOException, IOException {

        log.info("inside  removeObject with params objectKey={}",objectKey);

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(awsConfig.awsBucketName)
                .key(objectKey)
                .build();

        // Delete the object
        DeleteObjectResponse deleteObjectResponse = s3Client.deleteObject(deleteObjectRequest);
        log.info("Successfully deleted the document with objectKey={}, Response we got is response={}",objectKey, deleteObjectResponse);
        return Boolean.TRUE;
    }
}
