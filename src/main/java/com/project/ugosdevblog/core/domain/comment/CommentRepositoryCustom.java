package com.project.ugosdevblog.core.domain.comment;

import com.project.ugosdevblog.web.dto.commnet.CommentDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositoryCustom {
    List<CommentDto> getComment(Long contentId);
}
