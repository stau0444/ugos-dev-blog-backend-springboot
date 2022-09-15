package com.project.ugosdevblog.core.domain.user;

import com.project.ugosdevblog.core.domain.user.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<UserAuthority,Long> {
}
