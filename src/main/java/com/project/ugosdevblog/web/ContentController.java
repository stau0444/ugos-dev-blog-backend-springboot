package com.project.ugosdevblog.web;

import com.project.ugosdevblog.core.content.application.CommentManager;
import com.project.ugosdevblog.core.content.application.ContentManager;
import com.project.ugosdevblog.core.content.domain.CommentRepository;
import com.project.ugosdevblog.core.content.domain.ContentRepository;
import com.project.ugosdevblog.web.dto.commnet.CommentReq;
import com.project.ugosdevblog.web.dto.commnet.CommentResp;
import com.project.ugosdevblog.web.dto.content.ContentReq;
import com.project.ugosdevblog.web.dto.content.ContentResp;
import com.project.ugosdevblog.web.dto.content.SearchResp;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContentController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ContentManager contentManager;
    private final CommentManager commentManager;



    @GetMapping("/content/search")
    public Page<SearchResp> getSearchList(@RequestParam String keyword, Pageable pageable){
        return contentManager.search(keyword,pageable);
    }

    @GetMapping("/content/{id}")
    public ContentResp getContent(@PathVariable Long id){
        return contentManager.getContent(id);
    }

    @GetMapping("/contents")
    public Page<ContentResp> getContents(@RequestParam(defaultValue = "") String category  ,Pageable pageable ){
        return  contentManager.getContents(category,pageable);
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
        contentManager.saveContent(reqData);
    }

    @PutMapping("/content/{id}")
    public void updateContent(@PathVariable Long id,@RequestBody ContentReq reqData){
        contentManager.updateContent(id,reqData);
    }

    @DeleteMapping("content/{id}")
    public void deleteContent(@PathVariable Long id){
        contentManager.deleteById(id);
    }

    @GetMapping("/content/{id}/comment")
    public List<CommentResp> getCommentList(@PathVariable Long id){
        return commentManager.getComments(id);
    }
    @PostMapping("/content/{id}/comment")
    public void addComment(@PathVariable Long id, @RequestBody CommentReq commentReq){
        commentManager.addComment(id,commentReq);
    }
}
