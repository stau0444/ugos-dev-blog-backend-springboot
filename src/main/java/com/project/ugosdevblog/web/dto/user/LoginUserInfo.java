package com.project.ugosdevblog.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserInfo {

    private Long id;

    private String username;

    private String email;

    private String signUpAt;

    private String profileUrl;

    private boolean emailSubscribe;
}
