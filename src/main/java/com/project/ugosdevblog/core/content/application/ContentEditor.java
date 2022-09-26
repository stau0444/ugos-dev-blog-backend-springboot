package com.project.ugosdevblog.core.content.application;

import com.project.ugosdevblog.web.content.dto.ContentReq;

import java.io.IOException;

public interface ContentEditor {
     void saveContent(ContentReq reqData) throws IOException;
     void updateContent(Long id, ContentReq reqData) throws IOException;
     void deleteById(Long id);
}
