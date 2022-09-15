package com.project.ugosdevblog.core.content.infra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscribeUserMsgProps {
    private String username;
    private Long id;
    private String email;
}
