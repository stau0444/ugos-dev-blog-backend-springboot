package com.project.ugosdevblog.controller;

import com.project.ugosdevblog.dto.ContentListResp;
import com.project.ugosdevblog.dto.ContentResp;
import com.project.ugosdevblog.dto.PostContentReq;
import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.Tag;
import com.project.ugosdevblog.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
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
    @GetMapping("/contents")
    public ContentListResp getContents(@RequestParam String category ,@RequestParam  int page ){
        List<Content> contentList = contentRepository.findAll();

        System.out.println("category " + category + "page " + page);
        List<ContentResp> data = contentList
                .stream()
                .map(content ->
                        ContentResp.builder()
                                .article(content.getArticle())
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
                .description(content.getDescription())
                .article(content.getArticle())
                .build();

        contentRepository.save(newContent);

    }
}
