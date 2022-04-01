package com.project.ugosdevblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResp {
    private Long commentId;
    private String body;
    private Long repliedCommentId;
    private String createdAt;
    private String userName;
}
