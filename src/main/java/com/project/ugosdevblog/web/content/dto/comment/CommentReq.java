package com.project.ugosdevblog.web.content.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentReq {
    private String body;
    private Long repliedCommentId;
    private Long userId;
}
