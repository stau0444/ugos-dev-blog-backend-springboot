package com.project.ugosdevblog.dto;

import com.project.ugosdevblog.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
}
