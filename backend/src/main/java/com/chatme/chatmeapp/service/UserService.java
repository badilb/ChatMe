package com.chatme.chatmeapp.service;

import com.chatme.chatmeapp.models.entity.UserEntity;

public interface UserService {
    UserEntity findByUsername(String username);
    void saveUser(UserEntity userEntity);
    void deleteUserById(Long id);
}
