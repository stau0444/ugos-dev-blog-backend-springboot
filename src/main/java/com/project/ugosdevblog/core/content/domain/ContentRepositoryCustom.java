package com.project.ugosdevblog.core.content.domain;

import com.project.ugosdevblog.web.content.dto.ContentResp;
import com.project.ugosdevblog.web.content.dto.NextContentResp;
import com.project.ugosdevblog.web.content.dto.PrevContentResp;
import com.project.ugosdevblog.web.content.dto.SearchResp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepositoryCustom {
    Page<SearchResp> search(String keyword, Pageable pageable);

    Page<Content> findByTagsCustom(String tagName, Pageable pageable);

    PrevContentResp findPrevContent(Long id);

    NextContentResp findNextContent(Long id);

    ContentResp findContentById(Long id);
}
