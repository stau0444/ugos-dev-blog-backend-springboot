package com.project.ugosdevblog.web.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ymlValueTest {
    @Value("${app.sdk-access-key}")
    private String sdkAccessKey;

    @Value("${app.sdk-secret-key}")
    private String sdkSecretKey;

    @Value("${app.sdk-bucket-name}")
    private String sdkBucketName;

    @Test
    void sdkPropsTest(){
        System.out.println("sdkAccessKey = " + sdkAccessKey);
        System.out.println("sdkSecretKey = " + sdkSecretKey);
        System.out.println("sdkBucketName = " + sdkBucketName);
    }
}
