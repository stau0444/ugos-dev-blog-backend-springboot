package com.project.ugosdevblog.core.auth;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserAuthority.class)
@Builder
@EqualsAndHashCode
public class UserAuthority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Id
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
