package com.project.ugosdevblog.web.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPostReq {

    @ApiModelProperty(value = "유저 아이디")
    private String userId;
    @ApiModelProperty(value = "유저 비밀번호")
    private String password;
    @ApiModelProperty(value = "유저 이메일")
    private String email;
    @ApiModelProperty(value = "aws s3에 저장된 프로파일 이미지 주소")
    private String profile;
}
