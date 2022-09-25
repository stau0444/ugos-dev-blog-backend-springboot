package com.project.ugosdevblog.web.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContentReq {

    @ApiModelProperty(value = "컨텐츠 제목" ,required = true)
    private String title;

    @ApiModelProperty(value = "컨텐츠 내용" ,required = true)
    private String article;

    @ApiModelProperty(value = "컨텐츠 설명" ,required = true)
    private String description;

    @ApiModelProperty(value = "컨텐츠 관련 Tag" ,required = true)
    private List<String> tags;

    @ApiModelProperty(value = "컨텐츠 thumbnail img" ,required = true)
    private String imageUrl;

    @ApiModelProperty(value = "컨텐츠 작성자 id" ,required = true)
    private Long userId;
}
