package com.example.Task0.service;

import com.example.Task0.dao.RoleRepository;
import com.example.Task0.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceimp implements RoleService{
    private RoleRepository roleRepository;
    @Autowired
    public RoleServiceimp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }
}
