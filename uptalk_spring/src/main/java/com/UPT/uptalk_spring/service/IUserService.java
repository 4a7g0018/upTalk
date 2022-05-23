package com.UPT.uptalk_spring.service;

import com.UPT.uptalk_spring.model.UserInfo;

import java.util.List;

public interface IUserService {

    /**
     * 取得所有 User。
     */
    List<UserInfo> findAll();

    /**
     * 依照 email 取得 UserInfo。
     *
     * @param email 使用者的email
     */
    UserInfo findUserByUserEmail(String email);

    /**
     * 將 UserInfo 存 SQL。
     *
     * @param userInfo  要存入的UserInfo
     */
    UserInfo createUser(UserInfo userInfo);
}
