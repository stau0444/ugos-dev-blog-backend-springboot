package com.project.ugosdevblog.core.config;


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
@Component
public class DataSourceCredentialResourceLoader {

    private String username;
    private String password;
    private String url;
    private final String credentialPath;

    public DataSourceCredentialResourceLoader(
            @Value("${db.credential-path}")String credentialPath
    ) {
        this.credentialPath = credentialPath;
    }

    public Resource loadDataSourceCredential() {
        return new FileSystemResource(credentialPath);
    }

    public void setCredential(){

        Resource resource = loadDataSourceCredential();

        String dbCredential = null;

        try {
            dbCredential = new String(resource.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] split = dbCredential.split(",");
        List<String> collect = Arrays.stream(split)
                .collect(Collectors.toList());
        this.username = collect.get(0);
        this.password = collect.get(1);
        this.url = collect.get(2).replace("\n", "");
    }
}
