package com.project.ugosdevblog.exception;

public class NotExistUserException extends RuntimeException{

    public NotExistUserException(String message) {
        super(message);
    }
}
