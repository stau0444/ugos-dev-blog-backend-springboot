package com.project.ugosdevblog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/login/success")
    public String login(){
        System.out.println("login 성공");
        return "success";
    }}
