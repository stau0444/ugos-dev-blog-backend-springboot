package com.project.ugosdevblog.util;

import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.entity.Tag;
import com.project.ugosdevblog.repository.ContentRepository;
import com.project.ugosdevblog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
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
            tagRepository.saveAll(tagList);

            Content content = Content.builder()
                    .title("메이븐이란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content2 = Content.builder()
                    .title("메이븐이란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content3 = Content.builder()
                    .title("메이븐이란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 황경욱 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content4 = Content.builder()
                    .title("QUERYDSL이란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content5 = Content.builder()
                    .title("JPA란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content16 = Content.builder()
                    .title("DOCKER란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content7 = Content.builder()
                    .title("VUE란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content8 = Content.builder()
                    .title("자바스크립트란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content9 = Content.builder()
                    .title("리액트란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content10 = Content.builder()
                    .title("자바란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();

            List<Content> contentList = List.of(
                    content,
                    content2,
                    content3,
                    content4,
                    content5,
                    content16,
                    content7,
                    content8,
                    content9,
                    content10
            );

            repository.saveAll(contentList);
        }
    }
}
