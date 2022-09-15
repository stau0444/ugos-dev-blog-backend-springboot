package com.project.ugosdevblog.core.domain.content;

import com.project.ugosdevblog.core.domain.content.Content;
import com.project.ugosdevblog.core.domain.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ContentPagingRepository extends PagingAndSortingRepository<Content,Long> {

    Page<Content> findByTags(Tag tag, Pageable pageable);
}
