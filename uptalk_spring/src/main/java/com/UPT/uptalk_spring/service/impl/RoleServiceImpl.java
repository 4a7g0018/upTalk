package com.UPT.uptalk_spring.service.impl;

import com.UPT.uptalk_spring.model.Role;
import com.UPT.uptalk_spring.repository.RoleRepository;
import com.UPT.uptalk_spring.service.IRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @Title: RoleServiceImpl
 * @author: Benson-Yan
 * @version: 1.0.0
 * @time: 2022/5/17
 */

@Service
@AllArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {
        return this.roleRepository.save(role);
    }
}
