package com.project.ugosdevblog.util;

import com.project.ugosdevblog.entity.Content;
import com.project.ugosdevblog.repository.ContentRepository;
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

        @Transactional
        public void init(){
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
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content4 = Content.builder()
                    .title("메이븐이란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content5 = Content.builder()
                    .title("메이븐이란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content16 = Content.builder()
                    .title("메이븐이란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content7 = Content.builder()
                    .title("메이븐이란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content8 = Content.builder()
                    .title("메이븐이란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content9 = Content.builder()
                    .title("메이븐이란 무엇인가?")
                    .imageUrl("/IMG_1708.JPG")
                    .article("<p>asdddd</p>")
                    .createdAt(LocalDateTime.now())
                    .description("아파치 소프트웨어 재단에서 개발하는 java 기반 프로젝트의 라이프사이클 관리를 위한 빌드 도구. 이에 따라 컴파일과" +
                            " 빌드를 동시에 수행, 테스트를 병행하거나 서버 측 Deploy 자원을 관리할 수 있는 환경을 제공한다. 또한 라이브러리 " +
                            " 관리 기능도 내포하고 있다. Java로 개발하다 보면 다양한 라이브러리를 필요로 하게 되는데, settings.xml 또는 " +
                            " pom.xml porn이 아니다! 파일에 필요한 라이브러리만 적으면 maven이 알아서 다운받고 설치해주고 경로까지 지정해준다.")
                    .build();
            Content content10 = Content.builder()
                    .title("메이븐이란 무엇인가?")
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
