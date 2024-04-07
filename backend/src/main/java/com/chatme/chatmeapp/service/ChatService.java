package com.chatme.chatmeapp.service;

import com.chatme.chatmeapp.models.dto.ChatDTO;
import com.chatme.chatmeapp.models.entity.Chat;

import java.util.UUID;

public interface ChatService {
    void createChat(String requiredUsername);
    Chat findByChatUUID(UUID chatUUID);
    ChatDTO findDTOByChatUUID(UUID chatUUID);
    void deleteByChatUUID(UUID chatUUID);
}
