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
import java.util.UUID;

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
        String name="name";
        String email = "email";
        String password = "1234";
        String memberName="member";
        String adminName="admin";

        Role roleMember = roleRepository.findRoleByName(memberName);
        Role roleAdmin = roleRepository.findRoleByName(adminName);
        UserInfo userInfo = userRepository.findUserInfoByEmail(email);

        if (userInfo==null && roleMember==null && roleAdmin==null){
            Role member = new Role(memberName);
            Role admin = new Role(adminName);
            this.roleRepository.save(member);
            this.roleRepository.save(admin);

            Collection<Role> roles = new ArrayList<>();
            roles.add(member);
            roles.add(admin);

            UserInfo user = UserInfo.builder()
                    .id(UUID.randomUUID())
                    .name(name)
                    .email(email)
                    .password(password)
                    .enable(true)
                    .roles(roles).build();

            this.userRepository.save(user);
        }
    }
}
