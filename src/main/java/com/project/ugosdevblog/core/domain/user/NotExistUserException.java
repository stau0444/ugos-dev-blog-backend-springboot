package com.project.ugosdevblog.core.domain.user;

public class NotExistUserException extends RuntimeException{

    public NotExistUserException(String message) {
        super(message);
    }
}
