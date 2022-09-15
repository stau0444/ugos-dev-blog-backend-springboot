package com.project.ugosdevblog.core.user.domain;


import com.project.ugosdevblog.core.content.infra.SubscribeUserMsgProps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);
    @Query(value = "select new com.project.ugosdevblog.core.content.infra.SubscribeUserMsgProps(u.username,u.id,u.email) from User u where u.emailSubscribe = true")
    List<SubscribeUserMsgProps> findByEmailSubscribe();

}
