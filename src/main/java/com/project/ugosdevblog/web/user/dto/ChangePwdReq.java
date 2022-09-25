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
public class ChangePwdReq {
    @ApiModelProperty(value = "유저 이름")
    private String username;
    @ApiModelProperty(value = "유저 변경할 비밀번호")
    private String pwd;
}
