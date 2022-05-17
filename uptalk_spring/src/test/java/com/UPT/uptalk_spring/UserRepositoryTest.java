package com.UPT.uptalk_spring;

import com.UPT.uptalk_spring.model.Role;
import com.UPT.uptalk_spring.model.UserInfo;
import com.UPT.uptalk_spring.repository.RoleRepository;
import com.UPT.uptalk_spring.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Title: UserRepositoryTest
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/15
 */

@SpringBootTest
@Slf4j
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void saveTest() {
        Role member = new Role("member");
        Role admin = new Role("admin");
        this.roleRepository.save(member);
        this.roleRepository.save(admin);

        Collection<Role> roles = new ArrayList<>();
        roles.add(member);
        roles.add(admin);

        UserInfo user = UserInfo.builder()
                .name("name")
                .email("email")
                .password("1234")
                .enable(true)
                .roles(roles).build();

        this.userRepository.save(user);
    }

    @Test
    public void findUserTest() {
        UserInfo user = this.userRepository.findUserInfoByEmail("email");
        log.info("user : {}",user);
    }
}
