package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.entity.Content;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;


public interface ContentRepository extends JpaRepository<Content,Long>,ContentRepositoryCustom {

    @Query(value = "select c from Content c order by c.createdAt desc")
    Page<Content> findAllPagination(Pageable pageable);



}
