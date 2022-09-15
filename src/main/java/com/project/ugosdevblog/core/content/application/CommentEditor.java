package com.project.ugosdevblog.core.content.application;

import com.project.ugosdevblog.web.dto.commnet.CommentReq;

public interface CommentEditor {
    public void addComment(Long id, CommentReq commentReq);
}
