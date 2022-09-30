package com.project.ugosdevblog.web.user;


import com.project.ugosdevblog.web.common.S3ImageUploader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.File;

@SpringBootTest
public class S3UploaderTest {
    @Autowired
    S3ImageUploader uploader;

    @Test
    void uploadTest(){

    }
    @Test
    void deleteTest(){

    }
}
