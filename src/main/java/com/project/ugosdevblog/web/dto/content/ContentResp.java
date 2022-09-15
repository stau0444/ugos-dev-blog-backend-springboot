package com.project.ugosdevblog.web.dto.content;

import com.project.ugosdevblog.web.dto.commnet.CommentResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentResp {
    private Long id;
    private String title;
    private String createdAt;
    private String imageUrl;
    private String article;
    private String description;
    private Long userId;
    private List<String> tags;
    private List<CommentResp> comments;
    private PrevContentResp prevContent;
    private NextContentResp nextContent;
}
