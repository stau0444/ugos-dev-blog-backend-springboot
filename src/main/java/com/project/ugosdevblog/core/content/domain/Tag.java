package com.project.ugosdevblog.core.content.domain;

import com.project.ugosdevblog.core.content.domain.Content;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Tag {

    @Id
    @GeneratedValue
    private Long tagId;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "tags")
    private List<Content> contents = new ArrayList<>();

    private String tagName;

}
