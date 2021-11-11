package com.project.ugosdevblog.repository;

import com.project.ugosdevblog.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<UserAuthority,Long> {
}
