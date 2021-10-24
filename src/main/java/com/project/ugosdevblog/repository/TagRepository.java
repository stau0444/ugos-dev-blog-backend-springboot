package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByTagName(String tagName);
}
