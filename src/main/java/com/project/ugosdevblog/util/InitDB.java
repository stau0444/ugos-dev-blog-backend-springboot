package com.project.ugosdevblog.util;

import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.Tag;
import com.project.ugosdevblog.entity.User;
import com.project.ugosdevblog.repository.ContentRepository;
import com.project.ugosdevblog.repository.TagRepository;
import com.project.ugosdevblog.repository.UserRepository;
import com.project.ugosdevblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
            List<Tag> tagList = List.of(tag1, tag2, tag3, tag4, tag5, tag6, tag7, tag8, tag9);
            List<Tag> tags = tagRepository.saveAll(tagList);


        }
    }
}
