package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByTagName(String tagName);
}
