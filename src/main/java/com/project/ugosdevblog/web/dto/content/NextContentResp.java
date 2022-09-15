package com.project.ugosdevblog.web.dto.content;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class NextContentResp {
    private Long id;
    private String title;

    @QueryProjection
    public NextContentResp(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
