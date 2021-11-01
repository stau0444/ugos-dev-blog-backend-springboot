package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ContentRepository extends JpaRepository<Content,Long>,ContentRepositoryCustom {
}
