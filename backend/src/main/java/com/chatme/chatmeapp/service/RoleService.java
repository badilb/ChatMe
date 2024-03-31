package com.chatme.chatmeapp.service;

import com.chatme.chatmeapp.models.entity.Role;

public interface RoleService {
    Role findByName(String name);
}
