package com.project.ugosdevblog.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalAdvice{
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Map<String,Object>> AuthenticationException(AuthenticationException exception){
        System.out.println("exception" + exception);
        return ResponseEntity.status(401).body(Map.of("message","토큰이 만료됨","isVerified","false"));
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String,Object>> AuthenticationException(RuntimeException exception){
        System.out.println("exception" + exception);
        return ResponseEntity.status(401).body(Map.of("message","토큰이 만료됨","isVerified","false"));
    }
}
