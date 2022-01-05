package com.project.ugosdevblog.entity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(joinColumns = @JoinColumn(name = "content_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "user_id"))
    private User user;

    @Lob
    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
