package com.chatme.chatmeapp.service.impl;

import com.chatme.chatmeapp.models.dto.ChatDTO;
import com.chatme.chatmeapp.models.entity.Chat;
import com.chatme.chatmeapp.models.entity.UserEntity;
import com.chatme.chatmeapp.repository.ChatRepository;
import com.chatme.chatmeapp.repository.UserRepository;
import com.chatme.chatmeapp.service.ChatService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
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

    @Override
    public void deleteByChatUUID(UUID uuid) {
        try {
            chatRepository.deleteByChatUUID(uuid);
        } catch (Exception exception){
            log.error(exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void createChat(String usernameMember1, String usernameMember2) {
        try {
            // Create chat and save it
            UUID generatedUUID = UUID.randomUUID();
            Chat chat = new Chat();
            chat.setChatUUID(generatedUUID);
            chatRepository.save(chat);

            // Add chat to the users
            UserEntity member1 = userRepository.findByUsername(usernameMember1).orElseThrow();
            UserEntity member2 = userRepository.findByUsername(usernameMember2).orElseThrow();
            member1.getChats().add(chat);
            member2.getChats().add(chat);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }
}
