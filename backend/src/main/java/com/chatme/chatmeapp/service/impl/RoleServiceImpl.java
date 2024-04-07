package com.chatme.chatmeapp.service.impl;

import com.chatme.chatmeapp.exception.RoleNotFoundException;
import com.chatme.chatmeapp.models.entity.Role;
import com.chatme.chatmeapp.models.enums.RoleType;
import com.chatme.chatmeapp.repository.RoleRepository;
import com.chatme.chatmeapp.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(RoleType roleType) {
        return roleRepository.findByName(roleType.name())
                .orElseThrow(() -> new RoleNotFoundException(roleType.name()));
    }
}
