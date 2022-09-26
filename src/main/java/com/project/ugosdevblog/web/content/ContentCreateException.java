package com.project.ugosdevblog.web.content;

import java.io.IOException;

public class ContentCreateException extends RuntimeException {

    private final static String ERROR_MESSAGE = "컨텐츠를 생성하는중 에러가 발생하였습니다. 잠시 후에 다시 시도해 주세요.";
    public ContentCreateException() {
        super(ERROR_MESSAGE);
    }
}
