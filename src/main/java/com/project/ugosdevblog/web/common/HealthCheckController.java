package com.project.ugosdevblog.web.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public ResponseEntity<String> healthCheck(){
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"*");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
        headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");

        return ResponseEntity.status(200)
                .headers(headers).body("");
    }
}
