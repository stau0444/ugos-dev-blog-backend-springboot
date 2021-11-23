package com.project.ugosdevblog.entity;

import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String email;

    private LocalDateTime signUpAt;

    private LocalDateTime updateAt;

    private String profileUrl;

    @OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(name = "user_id"))
    private Set<UserAuthority> authorities = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<Content> contents = new ArrayList<>();

    private boolean emailSubscribe;

    private boolean enabled;

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

}
