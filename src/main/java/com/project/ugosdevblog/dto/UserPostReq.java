package com.project.ugosdevblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPostReq {
    private String userId;
    private String password;
    private String email;
    private String profile;
}
