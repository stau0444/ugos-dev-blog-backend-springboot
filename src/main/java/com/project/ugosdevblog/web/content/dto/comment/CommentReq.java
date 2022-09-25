package com.project.ugosdevblog.web.content.dto.comment;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "댓글 내용")
    private String body;
    @ApiModelProperty(value = "원글 id")
    private Long repliedCommentId;
    @ApiModelProperty(value = "작성자 id")
    private Long userId;
}
