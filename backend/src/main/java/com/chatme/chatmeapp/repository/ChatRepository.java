package com.chatme.chatmeapp.repository;

import com.chatme.chatmeapp.models.dto.ChatDTO;
import com.chatme.chatmeapp.models.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByChatUUID(UUID uuid);

    @Query("SELECT new com.chatme.chatmeapp.models.dto.ChatDTO(c) " +
            "FROM com.chatme.chatmeapp.models.entity.Chat c " +
            "WHERE c.chatUUID = :uuid")
    Optional<ChatDTO> findDTOByChatUUID(UUID uuid);
}
