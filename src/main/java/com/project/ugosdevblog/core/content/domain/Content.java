package com.project.ugosdevblog.core.content.domain;
import com.project.ugosdevblog.core.user.domain.User;
import lombok.*;

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
@EqualsAndHashCode
public class Content {

    @Id
    @GeneratedValue
    @Column(name="content_id")
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

    @OneToMany(mappedBy = "content" ,fetch = FetchType.LAZY ,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;

    @Lob
    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
