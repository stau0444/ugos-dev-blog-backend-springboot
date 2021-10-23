package com.project.ugosdevblog.controller;

import com.project.ugosdevblog.dto.ContentListResp;
import com.project.ugosdevblog.dto.ContentResp;
import com.project.ugosdevblog.dto.PostContentReq;
import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.Tag;
import com.project.ugosdevblog.repository.ContentRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Transactional
public class ContentController {

    private final ContentRepository contentRepository;

    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/test")
    public String Test(){
        System.out.println("테스트 요청 ");
        return "test 요청 응답 데이터";
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
                .tags(new ArrayList<>())
                .description(content.getDescription())
                .build();
    }

    @GetMapping("/contents")
    public ContentListResp getContents(@RequestParam String category ,@RequestParam  Integer page ){
        List<Content> contentList = contentRepository.findAll();

        System.out.println("category " + category + "page " + page);
        List<ContentResp> data = contentList
                .stream()
                .map(content ->
                        ContentResp.builder()
                                .article(content.getArticle())
                                .createdAt(DateTimeFormatter.ISO_LOCAL_DATE.format(content.getCreatedAt()))
                                .description(content.getDescription())
                                .id(content.getContentId())
                                .imageUrl(content.getImageUrl())
                                .tags(new ArrayList<>())
                                .title(content.getTitle())
                                .build()
                ).collect(Collectors.toList());

        return ContentListResp.builder()
                .data(data)
                .page(page)
                .totalCount(40)
                .build();

    }

    @PostMapping("/content")
    public void addContent(@RequestBody PostContentReq content){

        Content newContent = Content.builder()
                .title(content.getTitle())
                .imageUrl(content.getImageUrl())
                .createdAt(LocalDateTime.now())
                .description(content.getDescription())
                .article(content.getArticle())
                .build();

        contentRepository.save(newContent);

    }

    @PutMapping("/content/{id}")
    public void updateContent(@PathVariable Long id,@RequestBody PostContentReq reqData){

        System.out.println("reqdata:"+reqData);
        Optional<Content> contentOp = contentRepository.findById(id);

        Content content = contentOp.orElseThrow();

        content.setArticle(reqData.getArticle());
        content.setDescription(reqData.getDescription());
        content.setImageUrl(reqData.getImageUrl());
        content.setTitle(reqData.getTitle());
        content.setUpdatedAt(LocalDateTime.now());
    }

    @DeleteMapping("content/{id}")
    public void deleteContent(@PathVariable Long id){
        contentRepository.deleteById(id);

        new JPAQueryFactory().selectFrom()
    }
}
