package com.project.ugosdevblog.service;

import com.project.ugosdevblog.dto.ContentReq;
import com.project.ugosdevblog.dto.ContentResp;
import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.Tag;
import com.project.ugosdevblog.repository.ContentPagingRepository;
import com.project.ugosdevblog.repository.ContentRepository;
import com.project.ugosdevblog.repository.TagRepository;
import com.project.ugosdevblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

    public Page<ContentResp> getContents(String category, Pageable pageable){
        Page<Content> contentList = null;
        if(category.equals("")){
            contentList = contentRepository.findAll(pageable);
        }else{
            Tag tag = tagRepository.findByTagName(category);
            contentList = pagingRepository.findByTags(tag,pageable);
        }
        List<Content> result = contentList.getContent();
        List<ContentResp> data = result
                .stream()
                .map(content ->
                        ContentResp.builder()
                                .article(content.getArticle())
                                .createdAt(DateTimeFormatter.ISO_LOCAL_DATE.format(content.getCreatedAt()))
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
        Optional<Content> contentOp = contentRepository.findById(id);
        Content content = contentOp.orElseThrow();
        return ContentResp.builder()
                .id(content.getContentId())
                .article(content.getArticle())
                .userId(content.getUser().getId())
                .createdAt(DateTimeFormatter.ISO_LOCAL_DATE.format(content.getCreatedAt()))
                .imageUrl(content.getImageUrl())
                .title(content.getTitle())
                .tags(
                        content.getTags()
                                .stream()
                                .map(tag -> tag.getTagName())
                                .collect(Collectors.toList()))
                .description(content.getDescription())
                .build();
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
}
