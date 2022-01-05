package com.project.ugosdevblog.util;

import com.project.ugosdevblog.entity.Tag;
import com.project.ugosdevblog.repository.ContentRepository;
import com.project.ugosdevblog.repository.TagRepository;
import com.project.ugosdevblog.repository.UserRepository;
import com.project.ugosdevblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class InitDB {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init(){
        initMemberService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitMemberService{

        private final ContentRepository repository;
        private final TagRepository tagRepository;
        private final UserRepository userRepository;
        private final PasswordEncoder encoder;
        private final UserService userService;


        @Transactional
        public void init(){

            Tag tag1 = Tag.builder()
                    .tagName("JAVA")
                    .build();
            Tag tag2 = Tag.builder()
                    .tagName("JAVASCRIPT")
                    .build();
            Tag tag3 = Tag.builder()
                    .tagName("REACT")
                    .build();
            Tag tag4 = Tag.builder()
                    .tagName("JPA")
                    .build();
            Tag tag5 = Tag.builder()
                    .tagName("SPRING")
                    .build();
            Tag tag6 = Tag.builder()
                    .tagName("SPRING-SEC")
                    .build();
            Tag tag7 = Tag.builder()
                    .tagName("AWS")
                    .build();
            Tag tag8 = Tag.builder()
                    .tagName("WEB")
                    .build();
            Tag tag9 = Tag.builder()
                    .tagName("VUE")
                    .build();
            List<Tag> tagList = new ArrayList<>();
            tagList.add(tag1);
            tagList.add(tag2);
            tagList.add(tag3);
            tagList.add(tag4);
            tagList.add(tag5);
            tagList.add(tag6);
            tagList.add(tag7);
            tagList.add(tag8);
            tagList.add(tag9);

            tagRepository.saveAll(tagList);


        }
    }
}
