package com.project.ugosdevblog.dto;

import com.project.ugosdevblog.entity.Tag;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentReq {

    private String title;

    private String article;

    private String description;

    private List<String> tags;

    private String imageUrl;

    private Long userId;
}
