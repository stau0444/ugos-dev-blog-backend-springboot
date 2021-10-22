package com.project.ugosdevblog.dto;

import com.project.ugosdevblog.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Clob;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostContentReq {

    private String title;

    private String article;

    private String description;

    private List<Tag> tags;

    private String imageUrl;
}
