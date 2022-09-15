package com.project.ugosdevblog.core.user.domain;

public class ExistUserException extends RuntimeException{
    public ExistUserException(String message) {
        super(message);
    }
}
