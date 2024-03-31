package com.chatme.chatmeapp.service.impl;

import com.chatme.chatmeapp.models.entity.UserEntity;
import com.chatme.chatmeapp.repository.UserRepository;
import com.chatme.chatmeapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception e) {
            log.error("Error occurred while finding user by username: {}", username, e);
            return Optional.empty();
        }
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        try {
            userRepository.save(userEntity);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
