package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.dto.CommentDto;
import com.project.ugosdevblog.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositoryCustom {
    List<CommentDto> getComment(Long contentId);
}
