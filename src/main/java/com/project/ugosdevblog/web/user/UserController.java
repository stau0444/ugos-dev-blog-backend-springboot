package com.project.ugosdevblog.web.user;

import com.project.ugosdevblog.web.common.FileSizeLimitException;
import com.project.ugosdevblog.web.common.S3UploadFailedException;
import com.project.ugosdevblog.web.user.dto.ChangePwdReq;
import com.project.ugosdevblog.web.user.dto.FindPwdReq;
import com.project.ugosdevblog.web.user.dto.UpdateUserReq;
import com.project.ugosdevblog.core.user.application.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Api(value = "User API 정보를 제공하는 Controller")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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

    @PostMapping(value = "",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void saveUser(
            String userId,
            String password,
            String email,
            MultipartFile profile
    ) throws IOException {
        UserFormData userFormData = UserFormData.builder()
                .userId(userId)
                .password(password)
                .email(email)
                .profile(profile)
                .build();
        userService.saveUser(userFormData);
    }

    @PutMapping("")
    public void updateUser(UpdateUserReq reqData){
        try {
            userService.updateUserInfo(reqData);
        }catch(FileSizeLimitExceededException e) {
            throw new FileSizeLimitException();
        }catch (IOException e) {
            throw new S3UploadFailedException();
        }
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
        userService.changePwd(reqData.getUsername(),reqData.getPwd());
    }
}


