package com.project.ugosdevblog.web.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginStateResp {

    private boolean isLogin;

    private LoginUserInfo userInfo;
}
