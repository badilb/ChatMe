package com.chatme.chatmeapp.service.impl;

import com.chatme.chatmeapp.models.entity.Role;
import com.chatme.chatmeapp.repository.RoleRepository;
import com.chatme.chatmeapp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElseThrow();
    }
}
