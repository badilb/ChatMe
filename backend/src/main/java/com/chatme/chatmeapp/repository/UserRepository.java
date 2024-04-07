package com.chatme.chatmeapp.repository;

import com.chatme.chatmeapp.models.dto.UserEntityDTO;
import com.chatme.chatmeapp.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT new com.chatme.chatmeapp.models.dto.UserEntityDTO(u) " +
            "FROM com.chatme.chatmeapp.models.entity.UserEntity u " +
            "WHERE u.username = :username")
    Optional<UserEntityDTO> findDTOByUsername(String username);
}
