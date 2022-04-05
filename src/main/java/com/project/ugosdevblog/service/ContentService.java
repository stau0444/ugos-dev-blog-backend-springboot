package com.project.ugosdevblog.service;

import com.project.ugosdevblog.dto.*;
import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.Tag;
import com.project.ugosdevblog.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentService {

    private final ContentPagingRepository pagingRepository;
    private final ContentRepository contentRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public Page<ContentResp> getContents(String category, Pageable pageable){
        Page<Content> contentList = null;
        if(category.equals("")){
            contentList = contentRepository.findAllPagination(pageable);
        }else{
            Tag tag = tagRepository.findByTagName(category);
            contentList = contentRepository.findByTagsCustom(tag.getTagName(),pageable);
        }
        List<Content> result = contentList.getContent();
        List<ContentResp> data = result
                .stream()
                .map(content ->
                        ContentResp.builder()
                                .article(content.getArticle())
                                .createdAt(content.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 ")))
                                .description(content.getDescription())
                                .id(content.getContentId())
                                .imageUrl(content.getImageUrl())
                                .tags(
                                        content.getTags()
                                                .stream()
                                                .map(tag -> tag.getTagName())
                                                .collect(Collectors.toList()))
                                .title(content.getTitle())
                                .build()
                ).collect(Collectors.toList());
        long total = contentList.getTotalElements();
        System.out.println("total Count = " +total );
        return new PageImpl<>(data,pageable,total);
    }

    public ContentResp getContent(Long id) {
        ContentResp content = contentRepository.findContentById(id);
        List<String> tags = tagRepository.findSomeCase(id);
        PrevContentResp prevContent = contentRepository.findPrevContent(id);
        NextContentResp nextContent = contentRepository.findNextContent(id);
        content.setTags(tags);
        content.setPrevContent(prevContent);
        content.setNextContent(nextContent);
        return content;
    }

    public void saveContent(ContentReq reqData) {
        List<String> tags = reqData.getTags();

        Set<Tag> searchedTags = tags.stream().map((tag) ->
                tagRepository.findByTagName(tag)
        ).collect(Collectors.toSet());

        Content newContent = Content.builder()
                .title(reqData.getTitle())
                .imageUrl(reqData.getImageUrl())
                .createdAt(LocalDateTime.now())
                .tags(searchedTags)
                .user(userRepository.findById(reqData.getUserId()).orElseThrow())
                .description(reqData.getDescription())
                .article(reqData.getArticle())
                .build();

        contentRepository.save(newContent);
    }

    public void updateContent(Long id, ContentReq reqData) {
        Optional<Content> contentOp = contentRepository.findById(id);
        Set<Tag> selectedTags = reqData.getTags().stream().map(
                tag -> tagRepository.findByTagName(tag)
        ).collect(Collectors.toSet());
        Content content = contentOp.orElseThrow(()->new RuntimeException("아이디 잘못됨"));

        content.setArticle(reqData.getArticle());
        content.setDescription(reqData.getDescription());
        content.setImageUrl(reqData.getImageUrl());
        content.setTags(selectedTags);
        content.setTitle(reqData.getTitle());
        content.setUpdatedAt(LocalDateTime.now());
    }

    public Page<SearchResp> search(String keyword, Pageable pageable) {
        return contentRepository.search(keyword, pageable);
    }
}
