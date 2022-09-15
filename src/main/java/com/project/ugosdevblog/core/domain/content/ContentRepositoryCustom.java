package com.project.ugosdevblog.core.domain.content;

import com.project.ugosdevblog.web.dto.content.ContentResp;
import com.project.ugosdevblog.web.dto.content.NextContentResp;
import com.project.ugosdevblog.web.dto.content.PrevContentResp;
import com.project.ugosdevblog.web.dto.content.SearchResp;
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
