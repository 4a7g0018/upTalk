package com.UPT.uptalk_spring.service.impl;

import com.UPT.uptalk_spring.model.UserInfo;
import com.UPT.uptalk_spring.repository.UserRepository;
import com.UPT.uptalk_spring.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Title: UserServiceImpl
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/18
 */

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfo user = this.userRepository.findUserInfoByEmail(email);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user == null) {
            log.info("User not found !!");
            throw new UsernameNotFoundException("User not found !!");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->  authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new User(user.getEmail(), user.getPassword(), authorities);
    }

    /**
     * 取得所有 User。
     */
    @Override
    public List<UserInfo> findAll() {
        return this.userRepository.findAll();
    }

    /**
     * 依照 email 取得 UserInfo。
     *
     * @param email 使用者的email
     */
    @Override
    public UserInfo findUserByUserEmail(String email) {
        return this.userRepository.findUserInfoByEmail(email);
    }


    /**
     * 將 UserInfo 存 SQL。
     *
     * @param userInfo  要存入的UserInfo
     */
    @Override
    public UserInfo createUser(UserInfo userInfo) {
        return this.userRepository.save(userInfo);
    }

}
