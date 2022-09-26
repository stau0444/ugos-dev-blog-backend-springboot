package com.project.ugosdevblog.web.content.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

    @ApiModelProperty(value = "이전 thumbnail img url" ,required = true)
    private String imageBefore;

    @ApiModelProperty(value = "변경될 thumbnail img file" ,required = true)
    private MultipartFile image;

    @ApiModelProperty(value = "컨텐츠 작성자 id" ,required = true)
    private Long userId;
}
