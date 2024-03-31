package com.chatme.chatmeapp.service;

import com.chatme.chatmeapp.models.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findByUsername(String username);
    void saveUser(UserEntity userEntity);
    void deleteUserById(Long id);
}
