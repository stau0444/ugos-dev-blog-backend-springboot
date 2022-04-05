package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByTagName(String tagName);

    @Query(value = "select tag_name from tag t join content_tags ct,content c where t.tag_id = ct.tag_id and c.content_id = ct.content_id and c.content_id = :cId",nativeQuery = true)
    List<String> findSomeCase(@Param(("cId")) Long cId);
}
