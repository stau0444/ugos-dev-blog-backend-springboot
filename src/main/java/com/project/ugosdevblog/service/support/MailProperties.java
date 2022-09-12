package com.project.ugosdevblog.service.support;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mail")
@Getter @Setter
public  class MailProperties{
    private String AdminEmail;
    private String AdminPwd;
    private String subject;
    private String fromMail;
    private String fromName;

}
