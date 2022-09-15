package com.project.ugosdevblog.core.content.domain;

import com.project.ugosdevblog.core.content.domain.Content;
import com.project.ugosdevblog.core.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    private String body;

    private Long repliedCommentId;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private String replyTo;
}
