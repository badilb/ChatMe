package com.chatme.chatmeapp.service;

import com.chatme.chatmeapp.models.dto.ChatDTO;
import com.chatme.chatmeapp.models.entity.Chat;

import java.util.Optional;
import java.util.UUID;

public interface ChatService {
    Optional<Chat> findChatByChatUUID(UUID uuid);
    Optional<ChatDTO> findDTOByChatUUID(UUID uuid);
    void deleteByChatUUID(UUID uuid);
    void createChat(String usernameMember1, String usernameMember2);
}
