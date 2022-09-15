package com.project.ugosdevblog.web.dto.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenExpiredResp {
    private Exception exception;
    private boolean success;
}
