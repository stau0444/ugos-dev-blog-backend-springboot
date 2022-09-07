package com.project.ugosdevblog.controller;

import com.project.ugosdevblog.dto.*;

import com.project.ugosdevblog.repository.CommentRepository;
import com.project.ugosdevblog.repository.ContentRepository;
import com.project.ugosdevblog.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContentController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ContentRepository contentRepository;
    private final ContentService contentService;
    private final CommentRepository commentRepository;



    @GetMapping("/content/search")
    public Page<SearchResp> getSearchList(@RequestParam String keyword, Pageable pageable){
        logger.info("github hook test");
        return contentService.search(keyword,pageable);
    }

    @GetMapping("/content/{id}")
    public ContentResp getContent(@PathVariable Long id){
        return contentService.getContent(id);
    }

    @GetMapping("/contents")
    public Page<ContentResp> getContents(@RequestParam(defaultValue = "") String category  ,Pageable pageable ){
        return  contentService.getContents(category,pageable);
    }


    @GetMapping("/whitelist")
    public List<String> getWhiteList(@RequestParam(defaultValue = "검색어를 입력해주세요") String keyword){

        List<String> whiteListDB = List.of("자바", "자바스크립트", "스프링", "스프링부트", "젠킨스", "리액트","java","javascript");
        List<String> whiteList = new ArrayList<>();
        for (String s : whiteListDB) {
            if(s.startsWith(keyword)){
                whiteList.add(s);
            }
        }
        return whiteList;
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

    @GetMapping("/content/{id}/comment")
    public List<CommentResp> getCommentList(@PathVariable Long id){
        return contentService.getComments(id);
    }
    @PostMapping("/content/{id}/comment")
    public void addComment(@PathVariable Long id, @RequestBody CommentReq commentReq){
        contentService.addComment(id,commentReq);
    }
}
