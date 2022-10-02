package com.project.ugosdevblog.common.support;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Component
public class AwsCredentialResourceLoader {

    //aws
    private String accessKey;
    private String secretKey;

    private String credentialPath;
    private AwsCredentialResourceLoader(
            @Value("${app.aws.credential-path}") String credentialPath
    ){
        this.credentialPath = credentialPath;
    }
    private Resource loadAwsCredential() {
        return new FileSystemResource(credentialPath);
    }

    public void setKeys(){

        Resource resource = loadAwsCredential();

        String awsCredential = null;
        try {
            awsCredential = new String(resource.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] split = awsCredential.split(",");
        List<String> collect = Arrays.stream(split)
                .collect(Collectors.toList());
        this.accessKey = collect.get(0);
        this.secretKey = collect.get(1);
    }
}
