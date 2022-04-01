package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
@Transactional(readOnly = true)
public interface ContentPagingRepository extends PagingAndSortingRepository<Content,Long> {

    Page<Content> findByTags(Tag tag, Pageable pageable);
}
