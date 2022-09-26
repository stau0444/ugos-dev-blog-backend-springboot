package com.project.ugosdevblog.web.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
    @ApiModelProperty(value = "변경할 이미지 파일")
    private MultipartFile profile;
    @ApiModelProperty(value = "변경 이전 이미지 url")
    private String imageUrlBeforeUpdate;


}
