package com.project.ugosdevblog.dto;

import com.project.ugosdevblog.exception.NotValidTokenException;
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
