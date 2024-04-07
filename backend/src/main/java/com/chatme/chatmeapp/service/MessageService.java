package com.chatme.chatmeapp.service;

import com.chatme.chatmeapp.models.dto.MessageDTO;

import java.util.UUID;

public interface MessageService {
    MessageDTO findDTOByID(UUID chatUUID, Long messageID);
    void saveMessage(UUID chatUUID, String content);
    void updateMessageByID(UUID chatUUID, Long messageID, String content);
    void deleteMessageByID(UUID chatUUID, Long messageID);
}
