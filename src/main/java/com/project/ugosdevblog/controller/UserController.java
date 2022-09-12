package com.project.ugosdevblog.controller;

import com.project.ugosdevblog.dto.ChangePwdReq;
import com.project.ugosdevblog.dto.FindPwdReq;
import com.project.ugosdevblog.dto.UpdateUserReq;
import com.project.ugosdevblog.dto.UserPostReq;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder encoder;

    @GetMapping("/email-verify")
    public Integer emailVerify(String email){
        return userService.emailVerify(email);
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
                .profileUrl(reqData.getProfile())
                .username(reqData.getUserId())
                .signUpAt(LocalDateTime.now())
                .build();
        userService.saveUser(user);
    }

    @PutMapping("")
    public void updateUser(@RequestBody UpdateUserReq reqData){
        userService.updateUserInfo(reqData);
    }

    @GetMapping("/find-id")
    public void findId(@RequestParam String email){
        userService.findUserId(email);
    }

    @GetMapping("/find-pwd")
    public Integer getVerifyNum(FindPwdReq reqData){
        return userService.sendVerifyNum(reqData.getUserEmail(),reqData.getUserId());
    }
    @PutMapping("/change-pwd")
    public void changePwd(@RequestBody ChangePwdReq reqData){
        userService.changePwd(reqData.getUsername(),reqData.getPwd(),encoder);
    }
}


