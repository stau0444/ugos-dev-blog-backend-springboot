package com.project.ugosdevblog.web.content;

public class ContentUpdateException extends RuntimeException {

    private final static String ERROR_MESSAGE = "컨텐츠 수정 중 에러가 발생했습니다. 잠시후 다시 시도해 주세요.";

    public ContentUpdateException() {
        super(ERROR_MESSAGE);
    }
}
