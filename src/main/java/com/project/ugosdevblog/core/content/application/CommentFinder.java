package com.project.ugosdevblog.core.content.application;

import com.project.ugosdevblog.web.content.dto.comment.CommentResp;

import java.util.List;

public interface CommentFinder  {
    public List<CommentResp> getComments(Long id);
    Long getNextVal();
}
