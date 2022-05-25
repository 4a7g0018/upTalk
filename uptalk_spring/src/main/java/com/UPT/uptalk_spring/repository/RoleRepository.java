package com.UPT.uptalk_spring.repository;

import com.UPT.uptalk_spring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(String roleName);
}
