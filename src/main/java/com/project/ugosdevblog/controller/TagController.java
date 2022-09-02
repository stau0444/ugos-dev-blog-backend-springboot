package com.project.ugosdevblog.controller;

import com.project.ugosdevblog.entity.Tag;
import com.project.ugosdevblog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TagController {

    private final TagRepository tagRepository;

    @GetMapping("/tags")
    public List<String> getTags(){
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(tag -> tag.getTagName()).collect(Collectors.toList());
    }
    @PostMapping("/tag")
    public String addTag(@RequestParam String name){
        tagRepository.save(Tag.builder().tagName(name).build());
        return "tag 추가됨";
    }
}
