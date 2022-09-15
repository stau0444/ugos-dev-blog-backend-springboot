package com.project.ugosdevblog.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserReq {
    private String username;
    private String email;
    private boolean emailSubscribe;
    private String profileUrl;
}
