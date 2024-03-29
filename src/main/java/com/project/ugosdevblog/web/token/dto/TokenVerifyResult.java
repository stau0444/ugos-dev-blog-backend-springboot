package com.project.ugosdevblog.web.token.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenVerifyResult {
    private boolean success;
    private String username;
    private TokenFail fail;
}
