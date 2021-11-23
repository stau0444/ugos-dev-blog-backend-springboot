package com.project.ugosdevblog.dto;

import com.project.ugosdevblog.entity.UserAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginStateResp {

    private boolean isLogin;

    private LoginUserInfo userInfo;
}
