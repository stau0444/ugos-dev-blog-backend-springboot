package com.project.ugosdevblog.web.common;

public class FileSizeLimitException extends RuntimeException {

    private final static String ERROR_MESSAGE = "파일이 최대 파일사이즈(10Mb)를 초과했습니다. 다른 사진을 선택해 주세요";

    public FileSizeLimitException() {
        super(ERROR_MESSAGE);
    }
}
