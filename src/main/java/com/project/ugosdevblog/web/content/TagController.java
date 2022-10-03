package com.project.ugosdevblog.web.content;

import com.project.ugosdevblog.core.content.domain.Tag;
import com.project.ugosdevblog.core.content.domain.TagRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Tag API 정보를 제공하는 Controller")
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

    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name" , value = "태그명" ,required = true),
    })
    @PostMapping("/tag")
    public String addTag(@RequestParam String name){
        tagRepository.save(Tag.builder().tagName(name).build());
        return "tag 추가됨";
    }
}
