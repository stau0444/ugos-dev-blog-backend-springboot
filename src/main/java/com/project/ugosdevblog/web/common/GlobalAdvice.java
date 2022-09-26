package com.project.ugosdevblog.web.common;

import com.project.ugosdevblog.core.user.domain.ErrorMessage;
import com.project.ugosdevblog.core.user.domain.ExistUserException;
import com.project.ugosdevblog.core.user.domain.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalAdvice{


    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Map<String,Object>> AccessDeniedException(AccessDeniedException exception){
        return ResponseEntity.status(403).body(Map.of("message",exception.getMessage()));
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> UserNotFoundException(UserNotFoundException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder().message(exception.getMessage()).build());
    }
    @ExceptionHandler(value = ExistUserException.class)
    public ResponseEntity<ErrorMessage> ExistUserException(ExistUserException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = FileSizeLimitExceededException.class)
    public ResponseEntity<ErrorMessage> FileSizeLimitExceededException(FileSizeLimitExceededException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessage.builder().message(e.getMessage()).build());
    }


}
