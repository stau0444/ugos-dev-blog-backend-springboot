package com.project.ugosdevblog.web.dto.user;

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
