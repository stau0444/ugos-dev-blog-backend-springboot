package com.project.ugosdevblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchResp {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String createdAt;
}
