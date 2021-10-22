package com.project.ugosdevblog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {

    @Id @GeneratedValue
    private Long contentId;

    private String title;

    @Lob
    private String article;

    @Lob
    private String description;

    @OneToMany
    private List<Tag> tags;

    private String imageUrl;

}
