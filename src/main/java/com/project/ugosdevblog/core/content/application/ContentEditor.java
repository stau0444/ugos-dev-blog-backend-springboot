package com.project.ugosdevblog.core.content.application;

import com.project.ugosdevblog.web.dto.content.ContentReq;

public interface ContentEditor {
     void saveContent(ContentReq reqData);
     void updateContent(Long id, ContentReq reqData);
     void deleteById(Long id);
}
