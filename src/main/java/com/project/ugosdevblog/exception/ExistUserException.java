package com.project.ugosdevblog.exception;

public class ExistUserException extends RuntimeException{
    public ExistUserException(String message) {
        super(message);
    }
}
