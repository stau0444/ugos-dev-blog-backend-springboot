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
public class ContentDto {
    private Long id;
    private String article;
    private Long userId;
    private LocalDateTime createdAt;
    private String imageUrl;
    private String title;
    private String description;

    @QueryProjection
    public ContentDto(Long id, String article, Long userId, LocalDateTime createdAt, String imageUrl, String title, String description) {
        this.id = id;
        this.article = article;
        this.userId = userId;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
        this.title = title;
        this.description = description;
    }
}
