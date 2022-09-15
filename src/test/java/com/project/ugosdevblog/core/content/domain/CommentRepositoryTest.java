package com.project.ugosdevblog.core.content.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("prod")
class CommentRepositoryTest {

    @Autowired CommentRepository commentRepository;
    @Autowired ContentRepository contentRepository;

    @Test
    void getNextVal() {

        Long nextVal = commentRepository.getNextVal();
        System.out.println("nextVal = " + nextVal);
    }
}