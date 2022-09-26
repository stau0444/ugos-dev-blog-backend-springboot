package com.project.ugosdevblog.web.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFormData {
    private String userId;
    private String password;
    private String email;
    private MultipartFile profile;
}
