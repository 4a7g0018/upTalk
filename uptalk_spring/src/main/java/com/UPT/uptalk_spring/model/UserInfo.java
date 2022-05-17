package com.UPT.uptalk_spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 存放 user 帳號資訊
 *
 * @Title: UserInfo
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/11
 */

@Entity
@Table(name = "user_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enable", nullable = false)
    private boolean enable;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    public UserInfo(String name, String email, String password, boolean enable, Collection<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enable = enable;
        this.roles = roles;
    }
}
