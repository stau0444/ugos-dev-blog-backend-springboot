package com.project.ugosdevblog.web.content;

import com.project.ugosdevblog.core.content.application.CommentManager;
import com.project.ugosdevblog.core.content.application.ContentManager;
import com.project.ugosdevblog.web.content.dto.comment.CommentReq;
import com.project.ugosdevblog.web.content.dto.comment.CommentResp;
import com.project.ugosdevblog.web.content.dto.ContentReq;
import com.project.ugosdevblog.web.content.dto.ContentResp;
import com.project.ugosdevblog.web.content.dto.SearchResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(value = "Content API 정보를 제공하는 Controller")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContentController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ContentManager contentManager;
    private final CommentManager commentManager;



    @GetMapping("/content/search")
    public Page<SearchResp> getSearchList(
            @ApiParam(value = "검색어 키워드")
            @RequestParam String keyword,
            @ApiParam(value = "페이징 쿼리(sort ,  page , site)")
            Pageable pageable){
        return contentManager.search(keyword,pageable);
    }

    @GetMapping("/content/{id}")
    public ContentResp getContent(
            @ApiParam(value = "content ID")
            @PathVariable Long id
    ){
        return contentManager.getContent(id);
    }

    @GetMapping("/contents")
    public Page<ContentResp> getContents(
            @ApiParam(value = "컨텐츠 카테고리 , null = 전체 컨텐츠 조회")
            @RequestParam(defaultValue = "") String category  ,
            Pageable pageable
    ){
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
    public void updateContent(
            @ApiParam(value = "업데이트 컨텐츠 id")
            @PathVariable Long id,
            @RequestBody ContentReq reqData
    ){
        contentManager.updateContent(id,reqData);
    }

    @DeleteMapping("content/{id}")
    public void deleteContent(
            @ApiParam(value = "삭제할 컨텐츠 id")
            @PathVariable Long id
    ){
        contentManager.deleteById(id);
    }

    @GetMapping("/content/{id}/comment")
    public List<CommentResp> getCommentList(
            @ApiParam(value = "댓글이 속한 컨텐츠의 id")
            @PathVariable Long id
    ){
        return commentManager.getComments(id);
    }
    @PostMapping("/content/{id}/comment")
    public void addComment(
            @ApiParam(value = "댓글이 속한 컨텐츠의 id")
            @PathVariable Long id,
            @RequestBody CommentReq commentReq
    ){
        commentManager.addComment(id,commentReq);
    }
}
