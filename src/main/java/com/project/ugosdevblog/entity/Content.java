package com.project.ugosdevblog.entity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Content {

    @Id @GeneratedValue
    private Long contentId;

    private String title;

    @Lob
    private String article;

    @Lob
    private String description;

    @OneToMany(mappedBy = "content")
    private List<Tag> tags;

    @Lob
    private String imageUrl;

    @DateTimeFormat(pattern="yyyy.MM.dd")
    private LocalDateTime createdAt;

    @DateTimeFormat(pattern="yyyy.MM.dd")
    private LocalDateTime updatedAt;

}
