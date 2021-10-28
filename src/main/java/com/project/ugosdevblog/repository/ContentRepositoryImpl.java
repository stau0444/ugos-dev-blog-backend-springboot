package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.dto.QSearchListResp;
import com.project.ugosdevblog.dto.SearchListResp;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Locale;

import static com.project.ugosdevblog.entity.QContent.content;

@RequiredArgsConstructor
public class ContentRepositoryImpl implements  ContentRepositoryCustom{

    private final JPQLQueryFactory queryFactory;

    @Override
    public Page<SearchListResp> search(String keyword, Pageable pageable) {
        QueryResults<SearchListResp> searchResult = queryFactory
                .select(new QSearchListResp(
                        content.contentId,
                        content.title,
                        content.description,
                        content.imageUrl
                ))
                .from(content)
                .where(
                        content.title
                                .contains(keyword)
                                .or(content.title.contains(keyword.toUpperCase()))
                                .or(content.description.contains(keyword))
                                .or(content.description.contains(keyword.toUpperCase()))

                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<SearchListResp> searchList = searchResult.getResults();
        long totalCount = searchResult.getTotal();


        return new PageImpl<>(searchList,pageable,totalCount);
    }

}
