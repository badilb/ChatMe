package com.chatme.chatmeapp.service.impl;

import com.chatme.chatmeapp.exception.UserNotFoundException;
import com.chatme.chatmeapp.models.entity.UserEntity;
import com.chatme.chatmeapp.repository.UserRepository;
import com.chatme.chatmeapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    @Override
    public void saveUser(UserEntity userEntity) {
        userRepository.save(userEntity);
        log.info("User saved successfully: {}", userEntity.getUsername());
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }
}
