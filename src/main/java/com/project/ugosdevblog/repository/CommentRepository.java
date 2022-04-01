package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long>,CommentRepositoryCustom {
    @Query(value = "select nextval(comment_seq)" , nativeQuery = true)
    Long getNextVal();
}
