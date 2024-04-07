package com.chatme.chatmeapp.service;

import com.chatme.chatmeapp.models.entity.Role;
import com.chatme.chatmeapp.models.enums.RoleType;

public interface RoleService {
    Role findByName(RoleType roleType);
}
