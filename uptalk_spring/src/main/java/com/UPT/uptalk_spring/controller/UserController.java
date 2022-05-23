package com.UPT.uptalk_spring.controller;

import com.UPT.uptalk_spring.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: UserController
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/18
 */

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/find_all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(this.userService.findAll());
    }
}
