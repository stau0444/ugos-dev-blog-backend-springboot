package com.project.ugosdevblog.core.application;

import com.project.ugosdevblog.core.domain.comment.CommentRepository;
import com.project.ugosdevblog.core.domain.content.ContentRepository;
import com.project.ugosdevblog.core.domain.tag.TagRepository;
import com.project.ugosdevblog.core.domain.user.UserRepository;
import com.project.ugosdevblog.core.domain.comment.Comment;
import com.project.ugosdevblog.core.domain.content.Content;
import com.project.ugosdevblog.core.domain.tag.Tag;
import com.project.ugosdevblog.core.domain.user.User;
import com.project.ugosdevblog.core.infra.MailCommand;
import com.project.ugosdevblog.core.infra.MailSender;
import com.project.ugosdevblog.core.infra.SubscribeUserMsgProps;
import com.project.ugosdevblog.web.dto.commnet.CommentDto;
import com.project.ugosdevblog.web.dto.commnet.CommentReq;
import com.project.ugosdevblog.web.dto.commnet.CommentResp;
import com.project.ugosdevblog.web.dto.content.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentService {

    private final ContentRepository contentRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    private final MailSender mailSender;

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

        Set<Tag> searchedTags = tags.stream().map(tagRepository::findByTagName
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

        Content savedContent = contentRepository.save(newContent);
        List<SubscribeUserMsgProps> subscribedUser = userRepository.findByEmailSubscribe();
        for (SubscribeUserMsgProps userInfo : subscribedUser) {
            Map<String, String> msgProps = new HashMap<>();
            msgProps.put("username",userInfo.getUsername());
            msgProps.put("contentId",String.valueOf(savedContent.getContentId()));
            msgProps.put("contentTitle",savedContent.getTitle());
            mailSender.sendMail(userInfo.getEmail(), MailCommand.SUBSCRIBE,msgProps);
        }

    }

    public void updateContent(Long id, ContentReq reqData) {
        Optional<Content> contentOp = contentRepository.findById(id);
        Set<Tag> selectedTags = reqData.getTags().stream().map(
                tagRepository::findByTagName
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

    public void addComment(Long id, CommentReq commentReq) {
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
            Optional<Comment> isReplyTo = commentRepository.findById(commentReq.getRepliedCommentId());
            Comment repliedComment = isReplyTo.orElseThrow(RuntimeException::new);
            String replyTo = repliedComment.getUser().getUsername();
            Long repliedCommentId = repliedComment.getRepliedCommentId();
            comment = Comment.builder()
                    .id(nextVal)
                    .content(content)
                    .body(commentReq.getBody())
                    .repliedCommentId(repliedCommentId)
                    .replyTo(replyTo)
                    .createAt(LocalDateTime.now())
                    .user(user)
                    .build();
        }
        commentRepository.save(comment);
    }

    public List<CommentResp> getComments(Long id) {
        List<CommentDto> commentList = commentRepository.getComment(id);
        return commentList.stream().map((c) ->
                new CommentResp(
                        c.getCommentId(),
                        c.getBody(),
                        c.getRepliedCommentId(),
                        c.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 hh:mm:ss")),
                        c.getReplyTo(),
                        c.getUserName(),
                        c.getProfileUrl())
        ).collect(Collectors.toList());
    }
}
