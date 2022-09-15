package com.project.ugosdevblog.core.content.application;

import com.project.ugosdevblog.web.dto.commnet.CommentResp;

import java.util.List;

public interface CommentFinder  {
    public List<CommentResp> getComments(Long id);
    Long getNextVal();
}
