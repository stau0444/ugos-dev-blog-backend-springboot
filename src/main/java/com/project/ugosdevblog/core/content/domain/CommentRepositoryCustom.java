package com.project.ugosdevblog.core.content.domain;

import com.project.ugosdevblog.web.content.dto.comment.CommentDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositoryCustom {
    List<CommentDto> getComment(Long contentId);
}
