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
public class UpdateUserReq {

    @ApiModelProperty(value = "유저 이름")
    private String username;
    @ApiModelProperty(value = "유저 이메일")
    private String email;
    @ApiModelProperty(value = "이메일 구독여부")
    private boolean emailSubscribe;
    @ApiModelProperty(value = "s3에 저장된 프로파일 이미지 url")
    private String profileUrl;
}
