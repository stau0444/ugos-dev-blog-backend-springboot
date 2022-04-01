package com.project.ugosdevblog.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long commentId;
    private String body;
    private Long repliedCommentId;
    private LocalDateTime createdAt;
    private String userName;

    @QueryProjection
    public CommentDto(Long commentId, String body, Long repliedCommentId, LocalDateTime createdAt, String userName) {
        this.commentId = commentId;
        this.body = body;
        this.repliedCommentId = repliedCommentId;
        this.createdAt = createdAt;
        this.userName = userName;
    }
}
