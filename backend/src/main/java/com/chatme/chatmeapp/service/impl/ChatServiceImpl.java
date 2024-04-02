package com.chatme.chatmeapp.service.impl;

import com.chatme.chatmeapp.models.dto.ChatDTO;
import com.chatme.chatmeapp.models.entity.Chat;
import com.chatme.chatmeapp.repository.ChatRepository;
import com.chatme.chatmeapp.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Optional<Chat> findChatByChatUUID(UUID uuid) {
        try {
            return chatRepository.findByChatUUID(uuid);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<ChatDTO> findDTOByChatUUID(UUID uuid) {
        try {
            return chatRepository.findDTOByChatUUID(uuid);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
