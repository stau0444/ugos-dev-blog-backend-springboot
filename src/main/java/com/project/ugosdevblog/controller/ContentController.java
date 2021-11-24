package com.project.ugosdevblog.controller;

import com.project.ugosdevblog.dto.ContentResp;
import com.project.ugosdevblog.dto.ContentReq;
import com.project.ugosdevblog.dto.SearchListResp;
import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.Tag;
import com.project.ugosdevblog.repository.ContentRepository;
import com.project.ugosdevblog.repository.TagRepository;
import com.project.ugosdevblog.repository.UserRepository;
import com.project.ugosdevblog.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContentController {

    private final ContentRepository contentRepository;
    private final ContentService contentService;

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
        return contentService.getContent(id);
    }

    @GetMapping("/contents")
    public Page<ContentResp> getContents(@RequestParam(defaultValue = "") String category  ,Pageable pageable ){
        return  contentService.getContents(category,pageable);
    }

    @PostMapping("/content")
    public void addContent(@RequestBody ContentReq reqData){
        contentService.saveContent(reqData);
    }

    @PutMapping("/content/{id}")
    public void updateContent(@PathVariable Long id,@RequestBody ContentReq reqData){
       contentService.updateContent(id,reqData);
    }

    @DeleteMapping("content/{id}")
    public void deleteContent(@PathVariable Long id){
        contentRepository.deleteById(id);
    }
}
