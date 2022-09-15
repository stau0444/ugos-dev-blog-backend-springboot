package com.project.ugosdevblog.core.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<UserAuthority,Long> {
}
