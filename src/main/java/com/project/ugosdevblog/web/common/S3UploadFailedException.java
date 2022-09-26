package com.project.ugosdevblog.web.common;

public class S3UploadFailedException extends RuntimeException {
    private static final String ERROR_MESSAGE = "이미지 업로드 중 에러가 발생하였습니다. 다시 시도해 주세요";
    public S3UploadFailedException() {
        super(ERROR_MESSAGE);
    }
}
