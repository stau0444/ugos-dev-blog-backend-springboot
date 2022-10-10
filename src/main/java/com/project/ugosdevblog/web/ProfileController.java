package com.project.ugosdevblog.web;


import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final Environment env;

    @GetMapping("/profile")
    public String getApplicationProfiles(){
        List<String> activeProfiles = List.of(env.getActiveProfiles());
        return activeProfiles.get(0);
    }
}
