package com.project.ugosdevblog.core.content.application;

import com.project.ugosdevblog.web.dto.content.ContentResp;
import com.project.ugosdevblog.web.dto.content.SearchResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContentFinder {
    public ContentResp getContent(Long id);
    public Page<SearchResp> search(String keyword, Pageable pageable);
    public Page<ContentResp> getContents(String category, Pageable pageable);
}
