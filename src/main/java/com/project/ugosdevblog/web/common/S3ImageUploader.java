package com.project.ugosdevblog.web.common;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Component
public class S3ImageUploader {
    //Amazon-s3-sdk
    private AmazonS3 s3Client;

    final private String ACCESS_KEY = "AKIAVHMSY34H33KIV25X";
    final private String SECRET_KEY = "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";
    private Regions clientRegion = Regions.AP_NORTHEAST_2;
    private String bucket = "ugo-blog-image-bucket";

    private S3ImageUploader() {
        createS3Client();
    }

    //singleton pattern
    static private S3ImageUploader instance = null;

    public static S3ImageUploader getInstance() {
        if (instance == null) {
            return new S3ImageUploader();
        } else {
            return instance;
        }
    }

    //aws S3 client 생성
    private void createS3Client() {

        AWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY);

        this.s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(clientRegion)
                .build();
    }

    public void upload(File file, String key) {
        uploadToS3(new PutObjectRequest(this.bucket, key, file));
    }

    public void upload(InputStream is, String key, String contentType, long contentLength) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(contentType);
        objectMetadata.setContentLength(contentLength);

        uploadToS3(new PutObjectRequest(this.bucket, key, is, objectMetadata));
    }

    //PutObjectRequest는 Aws S3 버킷에 업로드할 객체 메타 데이터와 파일 데이터로 이루어져있다.
    private void uploadToS3(PutObjectRequest putObjectRequest) {

        try {
            this.s3Client.putObject(putObjectRequest);
            System.out.println(String.format("[%s] upload complete", putObjectRequest.getKey()));

        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String key) {
        try {
            //Delete 객체 생성
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucket, key);
            //Delete
            this.s3Client.deleteObject(deleteObjectRequest);
            System.out.println(String.format("[%s] deletion complete", key));

        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }
}
