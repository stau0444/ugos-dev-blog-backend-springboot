package com.project.ugosdevblog.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

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
