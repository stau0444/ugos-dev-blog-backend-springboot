package com.project.ugosdevblog.web.common.security;

import org.springframework.security.core.AuthenticationException;

public class NotValidTokenException extends AuthenticationException {

    public NotValidTokenException(String msg) {
        super(msg);
    }
}
