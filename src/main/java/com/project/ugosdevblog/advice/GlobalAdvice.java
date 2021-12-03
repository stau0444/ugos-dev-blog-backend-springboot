package com.project.ugosdevblog.advice;

import com.project.ugosdevblog.exception.ErrorMessage;
import com.project.ugosdevblog.exception.ExistUserException;
import com.project.ugosdevblog.exception.NotExistUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalAdvice{


    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Map<String,Object>> AccessDeniedException(AccessDeniedException exception){
        System.out.println("AccessDeniedException" + exception);
        return ResponseEntity.status(403).body(Map.of("message",exception.getMessage()));
    }

    @ExceptionHandler(value = NotExistUserException.class)
    public ResponseEntity<ErrorMessage> NotExistUserException(NotExistUserException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder().message(exception.getMessage()).build());
    }
    @ExceptionHandler(value = ExistUserException.class)
    public ResponseEntity<ErrorMessage> ExistUserException(ExistUserException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder().message(exception.getMessage()).build());
    }
}
