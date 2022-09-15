package com.project.ugosdevblog.core.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>,CommentRepositoryCustom {
    @Query(value = "select nextval(comment_seq)" , nativeQuery = true)
    Long getNextVal();
    @Query(value = "select username \n" +
            "from comment c \n" +
            "join user u \n" +
            "on c.user_id = u.id \n" +
            "where c.content_id = 1017 \n" +
            "and c.replied_comment_id != c.id \n" +
            "order by c.replied_comment_id asc ,c.create_at asc" , nativeQuery = true)
    List<String> getReplyTo(Long id);
}
