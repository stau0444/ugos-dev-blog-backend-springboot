package com.project.ugosdevblog.web.content.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class PrevContentResp {
    private Long id;
    private String title;

    @QueryProjection
    public PrevContentResp(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
