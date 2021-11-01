package com.project.ugosdevblog.controller;

import com.project.ugosdevblog.dto.ContentResp;
import com.project.ugosdevblog.dto.ContentReq;
import com.project.ugosdevblog.dto.SearchListResp;
import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.Tag;
import com.project.ugosdevblog.repository.ContentRepository;
import com.project.ugosdevblog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
public class ContentController {

    private final ContentRepository contentRepository;
    private final TagRepository tagRepository;
    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/test")
    public String Test(){
        return "test 요청 응답 데이터";
    }

    @GetMapping("/content/search")
    public Page<SearchListResp> getSearchList(@RequestParam String keyword, Pageable pageable){
        return contentRepository.search(keyword,pageable);
    }

    @GetMapping("/content/{id}")
    public ContentResp getContent(@PathVariable Long id){
        Optional<Content> contentOp = contentRepository.findById(id);
        Content content = contentOp.orElseThrow();

        return ContentResp.builder()
                .id(content.getContentId())
                .article(content.getArticle())
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

    @GetMapping("/contents")
    public Page<ContentResp> getContents(@RequestParam(defaultValue = "") String category  ,Pageable pageable ){
        Page<Content> contentList = null;
        if(category.equals("")){
            contentList = contentRepository.findAll(pageable);
        }else{
            contentList = contentRepository.findByTags(category,pageable);
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
        return new PageImpl<>(data,pageable,total);

    }


    @PostMapping("/content")
    public void addContent(@RequestBody ContentReq content){

        List<String> tags = content.getTags();

        List<Tag> searchedTags = tags.stream().map((tag) ->
                tagRepository.findByTagName(tag)
        ).collect(Collectors.toList());

        Content newContent = Content.builder()
                .title(content.getTitle())
                .imageUrl(content.getImageUrl())
                .createdAt(LocalDateTime.now())
                .tags(searchedTags)
                .description(content.getDescription())
                .article(content.getArticle())
                .build();

        contentRepository.save(newContent);

    }

    @PutMapping("/content/{id}")
    public void updateContent(@PathVariable Long id,@RequestBody ContentReq reqData){
        Optional<Content> contentOp = contentRepository.findById(id);
        System.out.println("contentID = "  + id);
        List<Tag> selectedTags = reqData.getTags().stream().map(
                tag -> tagRepository.findByTagName(tag)
        ).collect(Collectors.toList());
        Content content = contentOp.orElseThrow(()->new RuntimeException("아이디 잘못됨"));

        content.setArticle(reqData.getArticle());
        content.setDescription(reqData.getDescription());
        content.setImageUrl(reqData.getImageUrl());
        content.setTags(selectedTags);
        content.setTitle(reqData.getTitle());
        content.setUpdatedAt(LocalDateTime.now());
    }

    @DeleteMapping("content/{id}")
    public void deleteContent(@PathVariable Long id){
        contentRepository.deleteById(id);
    }
}
