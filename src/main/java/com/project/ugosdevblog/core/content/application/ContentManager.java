package com.project.ugosdevblog.core.content.application;

import com.project.ugosdevblog.core.content.domain.*;
import com.project.ugosdevblog.core.content.infra.MailCommand;
import com.project.ugosdevblog.core.content.infra.MailSender;
import com.project.ugosdevblog.core.content.infra.SubscribeUserMsgProps;
import com.project.ugosdevblog.core.user.domain.UserRepository;
import com.project.ugosdevblog.web.common.S3ImageUploader;
import com.project.ugosdevblog.web.content.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContentManager implements ContentEditor,ContentFinder{

    private final ContentRepository contentRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final MailSender mailSender;

    private final S3ImageUploader s3ImageUploader;

    @Value("${app.sdk-host}")
    private  String sdkHost;

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Page<SearchResp> search(String keyword, Pageable pageable) {
        return contentRepository.search(keyword, pageable);
    }

    @Transactional
    public void saveContent(ContentReq reqData) throws IOException {
        List<String> tags = reqData.getTags();
        MultipartFile contentImg = reqData.getImage();
        long timeStamp = System.currentTimeMillis();
        String imgKey = timeStamp+":content:"+contentImg.getOriginalFilename();
        String host = sdkHost;
        s3ImageUploader.upload(
                contentImg.getInputStream(),
                imgKey,
                contentImg.getContentType(),
                contentImg.getSize()
        );
        Set<Tag> searchedTags = tags.stream().map(tagRepository::findByTagName
        ).collect(Collectors.toSet());

        Content newContent = Content.builder()
                .title(reqData.getTitle())
                .imageUrl(host+imgKey)
                .createdAt(LocalDateTime.now())
                .tags(searchedTags)
                .user(userRepository.findById(reqData.getUserId()).orElseThrow())
                .description(reqData.getDescription())
                .article(reqData.getArticle())
                .build();

        Content savedContent = contentRepository.save(newContent);
        List<SubscribeUserMsgProps> subscribedUser = userRepository.findByEmailSubscribe();
        mailSender.sendMailToSubscriber(subscribedUser,savedContent.getTitle(),savedContent.getContentId());

    }
    @Transactional
    public void updateContent(Long id, ContentReq reqData) throws IOException {

        MultipartFile image = reqData.getImage();
        long timeStamp = System.currentTimeMillis();
        String updateImgKey = timeStamp+":content:"+image.getOriginalFilename();
        String host = sdkHost;
        Optional<Content> contentOp = contentRepository.findById(id);
        Set<Tag> selectedTags = reqData.getTags().stream().map(
                tagRepository::findByTagName
        ).collect(Collectors.toSet());

        String deleteImgKey = reqData.getImageBefore().substring(62);
        s3ImageUploader.delete(deleteImgKey);

        s3ImageUploader.upload(
            image.getInputStream(),
            updateImgKey,
            image.getContentType(),
            image.getSize()
        );

        Content content = contentOp.orElseThrow(ContentNotFoundException::new);

        content.setArticle(reqData.getArticle());
        content.setDescription(reqData.getDescription());
        content.setImageUrl(host+updateImgKey);
        content.setTags(selectedTags);
        content.setTitle(reqData.getTitle());
        content.setUpdatedAt(LocalDateTime.now());
    }


    public void deleteById(Long id) {
        contentRepository.deleteById(id);
    }
}
