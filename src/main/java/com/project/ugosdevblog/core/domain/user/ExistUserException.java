package com.project.ugosdevblog.core.domain.user;

public class ExistUserException extends RuntimeException{
    public ExistUserException(String message) {
        super(message);
    }
}
