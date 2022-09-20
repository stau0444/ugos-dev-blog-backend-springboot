package com.project.ugosdevblog.core.content.domain;

public class ContentNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "컨텐츠를 찾을 수 없습니다";
    public ContentNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
