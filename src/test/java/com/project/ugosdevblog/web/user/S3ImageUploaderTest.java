package com.project.ugosdevblog.web.user;

import com.project.ugosdevblog.web.common.S3ImageUploader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class S3ImageUploaderTest {

    private final S3ImageUploader instance = S3ImageUploader.getInstance();

    @Test
    void upload() {
        File file =  new File("/Users/ugo/IdeaProjects/ugosdevblog/src/main/resources/static/banner.png");

        instance.upload(file,file.getName()+":profile");
    }

    @Test
    void delete() {

        instance.delete("사진 2021. 12. 23. 오후 6.19 #2.jpg:profile");
    }
    @Test
    void subStringTest(){
        String a = "https://ugo-blog-image-bucket.s3.ap-northeast-2.amazonaws.com/asdasd";
        String substring = a.substring(62);
        System.out.println("substring = " + substring);
        Assertions.assertEquals("asdasd",substring);

    }
}