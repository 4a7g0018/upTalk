package com.UPT.uptalk_spring.repository;

import com.UPT.uptalk_spring.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findUserInfoByEmail(String email);
}
