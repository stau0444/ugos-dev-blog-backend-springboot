package com.project.ugosdevblog.core.domain.user;

import org.springframework.security.core.AuthenticationException;

public class NotValidTokenException extends AuthenticationException {

    public NotValidTokenException(String msg) {
        super(msg);
    }
}
