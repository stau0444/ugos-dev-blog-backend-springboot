package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.dto.NextContentResp;
import com.project.ugosdevblog.dto.PrevContentResp;
import com.project.ugosdevblog.dto.SearchListResp;
import com.project.ugosdevblog.dto.SearchResp;
import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface ContentRepositoryCustom {
    Page<SearchResp> search(String keyword, Pageable pageable);

    Page<Content> findByTagsCustom(String tagName, Pageable pageable);

    PrevContentResp findPrevContent(Long id);

    NextContentResp findNextContent(Long id);
}
