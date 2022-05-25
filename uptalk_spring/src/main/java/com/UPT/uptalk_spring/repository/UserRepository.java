package com.UPT.uptalk_spring.repository;

import com.UPT.uptalk_spring.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserInfo, UUID> {

    UserInfo findUserInfoByEmail(String email);
}
