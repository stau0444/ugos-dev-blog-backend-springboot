package com.project.ugosdevblog.web.user;

import com.project.ugosdevblog.web.user.dto.ChangePwdReq;
import com.project.ugosdevblog.web.user.dto.FindPwdReq;
import com.project.ugosdevblog.web.user.dto.UpdateUserReq;
import com.project.ugosdevblog.web.user.dto.UserPostReq;
import com.project.ugosdevblog.core.user.domain.User;
import com.project.ugosdevblog.core.user.application.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;

@Api(value = "User API 정보를 제공하는 Controller")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder encoder;

    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "email" , value = "검증할 email 주소" ,required = true)
    })
    @GetMapping("/email-verify")
    public Integer emailVerify(String email){
        return userService.emailVerify(email);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "userId" , value = "중복확인할 유저의 ID" ,required = true)
    })
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


    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "email" , value = "인증번호 를 전송할 메일 주소" ,required = true)
    })
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


