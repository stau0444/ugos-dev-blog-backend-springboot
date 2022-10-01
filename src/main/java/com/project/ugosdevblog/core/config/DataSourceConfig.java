package com.project.ugosdevblog.core.config;



import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class DataSourceConfig {

    private final String username;
    private final String password;
    private final String dataSourceUrl;
    public DataSourceConfig(DataSourceCredentialResourceLoader credentialResourceLoader) {
        credentialResourceLoader.setCredential();
        this.username = credentialResourceLoader.getUsername();
        this.password = credentialResourceLoader.getPassword();
        this.dataSourceUrl = credentialResourceLoader.getUrl();
    }

    @Profile("prod")
    @Bean
    public DataSource dataSourceProd(
    ){
        return DataSourceBuilder.create()
                .username(this.username)
                .password(this.password)
                .url(this.dataSourceUrl)
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
