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
public class FindPwdReq {

    @ApiModelProperty(value = "유저 아이디")
    private String userId;
    @ApiModelProperty(value = "유저 이메일")
    private String userEmail;
}
