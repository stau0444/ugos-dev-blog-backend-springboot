package com.project.ugosdevblog.controller;

import com.project.ugosdevblog.dto.UserPostReq;
import com.project.ugosdevblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/duplication-check")
    public boolean isDuplicatedId(@RequestParam String userId){
        return userService.CheckDuplication(userId);
    }

    @PostMapping("")
    public void saveUser(@RequestBody UserPostReq reqData){
        userService.saveUser(reqData);
    }
}


