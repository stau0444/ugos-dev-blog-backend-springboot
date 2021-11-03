package com.project.ugosdevblog.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserAuthority.class)
@Builder
public class UserAuthority implements GrantedAuthority {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Id
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
