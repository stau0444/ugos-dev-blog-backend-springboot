package com.project.ugosdevblog.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchListResp {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private LocalDateTime createAt;

    @QueryProjection
    public SearchListResp(Long id, String title, String description, String imageUrl , LocalDateTime createAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createAt = createAt;
    }
}
