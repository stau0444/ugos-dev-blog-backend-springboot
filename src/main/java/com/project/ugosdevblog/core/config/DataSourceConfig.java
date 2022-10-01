package com.project.ugosdevblog.core.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    private final  DataSourceCredentialResourceLoader credentialResourceLoader;

    public DataSourceConfig(DataSourceCredentialResourceLoader credentialResourceLoader) {
        this.credentialResourceLoader = credentialResourceLoader;
        credentialResourceLoader.setCredential();
    }

    @Profile("prod")
    @Bean
    public DataSource dataSourceProd(
    ){
        return DataSourceBuilder.create()
                .username(credentialResourceLoader.getUsername())
                .password(credentialResourceLoader.getPassword())
                .url(credentialResourceLoader.getUrl())
                .driverClassName("org.mariadb.jdbc.Driver")
                .build();
    }
    @Profile("dev")
    @Bean
    public DataSource dataSourceDev(){
        return DataSourceBuilder.create()
                .username("sa")
                .password("")
                .url("jdbc:h2:tcp://localhost/~/ugosdevblog")
                .driverClassName("org.h2.Driver")
                .build();
    }
}