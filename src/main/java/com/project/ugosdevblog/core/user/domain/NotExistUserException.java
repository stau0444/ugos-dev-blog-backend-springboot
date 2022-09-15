package com.project.ugosdevblog.core.user.domain;

public class NotExistUserException extends RuntimeException{

    public NotExistUserException(String message) {
        super(message);
    }
}
