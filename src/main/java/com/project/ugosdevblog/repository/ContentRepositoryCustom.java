package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.dto.SearchListResp;
import com.project.ugosdevblog.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentRepositoryCustom {
    Page<SearchListResp> search(String keyword, Pageable pageable);
    Page<Content> findByTags(String category, Pageable pageable);
}
