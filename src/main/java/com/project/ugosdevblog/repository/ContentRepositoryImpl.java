package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.dto.*;
import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.QContent;
import com.project.ugosdevblog.entity.QTag;
import com.project.ugosdevblog.entity.Tag;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.ugosdevblog.entity.QContent.content;

@RequiredArgsConstructor
public class ContentRepositoryImpl implements  ContentRepositoryCustom{

    private final JPQLQueryFactory queryFactory;

    @Override
    public Page<SearchResp> search(String keyword, Pageable pageable) {
        QueryResults<SearchListResp> searchResult = queryFactory
                .select(new QSearchListResp(
                        content.contentId,
                        content.title,
                        content.description,
                        content.imageUrl,
                        content.createdAt
                ))
                .from(content)
                .where(
                        content.title
                                .contains(keyword)
                                .or(content.title.contains(keyword.toUpperCase()))
                                .or(content.description.contains(keyword))
                                .or(content.description.contains(keyword.toUpperCase()))

                )
                .orderBy(content.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<SearchListResp> searchList = searchResult.getResults();
        List<SearchResp> searchResp = searchList.stream().map((c) ->
                new SearchResp(c.getId(), c.getTitle(), c.getDescription(), c.getImageUrl(), c.getCreateAt().format(DateTimeFormatter.ofPattern(("yyyy년 MM월 dd일"))))
        ).collect(Collectors.toList());
        long totalCount = searchResult.getTotal();


        return new PageImpl<>(searchResp,pageable,totalCount);
    }

    @Override
    public PrevContentResp findPrevContent(Long id) {
        Content selectedContent = queryFactory.selectFrom(content).where(content.contentId.eq(id)).fetchOne();
        return queryFactory.select(new QPrevContentResp(content.contentId, content.title))
                .from(content)
                .where(content.createdAt.lt(selectedContent.getCreatedAt()))
                .orderBy(content.createdAt.desc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public NextContentResp findNextContent(Long id) {
        Content selectedContent = queryFactory.selectFrom(content).where(content.contentId.eq(id)).fetchOne();
        return queryFactory.select(new QNextContentResp(content.contentId, content.title))
                .from(content)
                .where(content.createdAt.gt(selectedContent.getCreatedAt()))
                .limit(1)
                .fetchOne();
    }

    @Override
    public Page<Content> findByTagsCustom(String tagName, Pageable pageable) {
        Tag selectedTag = queryFactory.selectFrom(QTag.tag).where(QTag.tag.tagName.eq(tagName)).fetchOne();

        QueryResults<Content> contentsByTag = queryFactory
                .selectDistinct(content)
                .from(content)
                .orderBy(content.createdAt.desc())
                .where(content.tags.contains(selectedTag))
                .fetchResults();

        List<Content> results = contentsByTag.getResults();
        long totalCount = contentsByTag.getTotal();
        return new PageImpl<>(results,pageable,totalCount);
    }

}
