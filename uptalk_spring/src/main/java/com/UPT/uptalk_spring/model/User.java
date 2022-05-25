package com.UPT.uptalk_spring.model;

import com.UPT.uptalk_spring.service.IRoomService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: User
 * @author: David-Liao
 * @version: 1.0.0
 * @time: 2022/5/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private IRoomService roomService;
    private UserInfo userInfo;
}
