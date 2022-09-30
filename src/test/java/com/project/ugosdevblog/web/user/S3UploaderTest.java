package com.project.ugosdevblog.web.user;


import com.project.ugosdevblog.web.common.S3ImageUploader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
public class S3UploaderTest {
    @Autowired
    S3ImageUploader uploader;

    @Test
    void uploadTest(){
        File file = new File("/Users/ugo/IdeaProjects/ugosdevblog/src/main/resources/static/banner.png");
        uploader.upload(file,"test banner.png");
    }
    @Test
    void deleteTest(){
        uploader.delete("test banner.png");
    }
}
