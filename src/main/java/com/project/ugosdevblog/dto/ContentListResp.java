package com.project.ugosdevblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentListResp {
    private List<ContentResp> data;
    private int page;
    private int totalCount;
}
