package com.project.ugosdevblog.controller;

import com.project.ugosdevblog.dto.*;
import com.project.ugosdevblog.entity.Comment;
import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.Tag;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.repository.CommentRepository;
import com.project.ugosdevblog.repository.ContentRepository;
import com.project.ugosdevblog.repository.TagRepository;
import com.project.ugosdevblog.repository.UserRepository;
import com.project.ugosdevblog.service.ContentService;
import com.project.ugosdevblog.util.UnicodeHandler;
import com.querydsl.jpa.hibernate.HibernateQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.hibernate.jdbc.ReturningWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Query;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ContentController {

    private final ContentRepository contentRepository;
    private final ContentService contentService;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UnicodeHandler unicodeHandler;

    @GetMapping("/")
    public String home(){
        return "home";
    }
    @GetMapping("/test")
    public String Test(){
        return "test 요청 응답 데이터";
    }

    @GetMapping("/content/search")
    public Page<SearchResp> getSearchList(@RequestParam String keyword, Pageable pageable){
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
    @GetMapping("/tags")
    public List<String> getTags(){
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(tag -> tag.getTagName()).collect(Collectors.toList());
    }
    @PostMapping("/tag")
    public String addTag(@RequestParam String name){
        tagRepository.save(Tag.builder().tagName(name).build());
        return "tag 추가됨";
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
        List<CommentDto> commentList = commentRepository.getComment(id);
        return commentList.stream().map((c) ->
                new CommentResp(
                        c.getCommentId(),
                        c.getBody(),
                        c.getRepliedCommentId(),
                        c.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 hh:mm:ss")),
                        c.getUserName())).collect(Collectors.toList());
    }
    @PostMapping("/content/{id}/comment")
    public void addComment(@PathVariable Long id, @RequestBody CommentReq commentReq){
        Long nextVal = commentRepository.getNextVal();
        Optional<Content> byId = contentRepository.findById(id);
        Content content = byId.orElseThrow(RuntimeException::new);
        User user =  userRepository.findById(commentReq.getUserId()).orElseThrow(RuntimeException::new);
        Comment comment = null;
        if(commentReq.getRepliedCommentId()==null){
            comment = Comment.builder()
                    .id(nextVal)
                    .content(content)
                    .body(commentReq.getBody())
                    .repliedCommentId(nextVal)
                    .createAt(LocalDateTime.now())
                    .user(user)
                    .build();
        }else{
            comment = Comment.builder()
                    .id(nextVal)
                    .content(content)
                    .body(commentReq.getBody())
                    .repliedCommentId(commentReq.getRepliedCommentId())
                    .createAt(LocalDateTime.now())
                    .user(user)
                    .build();
        }
        commentRepository.save(comment);

    }
}
