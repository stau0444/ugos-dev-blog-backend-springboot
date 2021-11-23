package com.project.ugosdevblog.advice;

import lombok.extern.slf4j.Slf4j;
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

}
