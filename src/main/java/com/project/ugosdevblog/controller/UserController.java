package com.project.ugosdevblog.controller;

import com.project.ugosdevblog.dto.LoginReq;
import com.project.ugosdevblog.dto.UserPostReq;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.entity.UserAuthority;
import com.project.ugosdevblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder encoder;

    @GetMapping("/test")
    public String test(){
        String message = "로그인 테스트";
        return message;
    }

    @GetMapping("/duplication-check")
    public boolean isDuplicatedId(@RequestParam String userId){
        return userService.CheckDuplication(userId);
    }

    @PostMapping("")
    public void saveUser(@RequestBody UserPostReq reqData){
        User user = User.builder()
                .email(reqData.getEmail())
                .password(encoder.encode(reqData.getPassword()))
                .emailSubscribe(false)
                .enabled(true)
                .username(reqData.getUserId())
                .signUpAt(LocalDateTime.now())
                .build();
        userService.saveUser(user);
    }
}


