package com.project.ugosdevblog;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UgosdevblogApplicationTests {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Test
    void contextLoads() {

    }

    @Test
    void gitBranchTest(){
        logger.info("added development branch");
    }

}
