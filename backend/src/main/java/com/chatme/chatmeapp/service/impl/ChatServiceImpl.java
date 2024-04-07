package com.chatme.chatmeapp.service.impl;

import com.chatme.chatmeapp.exception.ChatNotFoundException;
import com.chatme.chatmeapp.exception.UserNotFoundException;
import com.chatme.chatmeapp.models.dto.ChatDTO;
import com.chatme.chatmeapp.models.entity.Chat;
import com.chatme.chatmeapp.models.entity.UserEntity;
import com.chatme.chatmeapp.repository.ChatRepository;
import com.chatme.chatmeapp.repository.UserRepository;
import com.chatme.chatmeapp.service.ChatService;
import com.chatme.chatmeapp.utils.SessionUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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
    @PreAuthorize("@chatValidation.userOwnsTheChat(#chatUUID)")
//    @Cacheable(cacheNames = "CHAT_CACHE", key = "'chat_uuid' + #chatUUID", unless = "#result == null")
    public ChatDTO findDTOByChatUUID(UUID chatUUID) {
        return chatRepository.findDTOByChatUUID(chatUUID)
                .orElseThrow(() -> new ChatNotFoundException(chatUUID));
    }

    private Chat createAndSaveChat() {
        UUID generatedUUID = UUID.randomUUID();
        Chat chat = new Chat();
        chat.setChatUUID(generatedUUID);
        return chatRepository.save(chat);
    }

    @Override
    @PreAuthorize("!(@chatValidation.hasUserChatWithThisUsername(#requiredUsername))")
    @Transactional
    public void createChat(String requiredUsername) {
        // Create chat and save it
        Chat chat = createAndSaveChat();

        // Add chat to the users
        UserEntity member1 = getUserByUsername(SessionUtil.getSessionUsername());
        UserEntity member2 = getUserByUsername(requiredUsername);
        addChatToUsers(chat, member1, member2);
    }
    private UserEntity getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
    private void addChatToUsers(Chat chat, UserEntity member1, UserEntity member2) {
        member1.getChats().add(chat);
        member2.getChats().add(chat);
    }

    @Override
    @PreAuthorize("@chatValidation.userOwnsTheChat(#chatUUID)")
    public Chat findByChatUUID(UUID chatUUID) {
        return chatRepository.findByChatUUID(chatUUID)
                .orElseThrow(() -> new ChatNotFoundException(chatUUID));
    }

    @Override
    @PreAuthorize("@chatValidation.userOwnsTheChat(#chatUUID)")
    public void deleteByChatUUID(UUID chatUUID) {
        Chat chat = chatRepository.findByChatUUID(chatUUID)
                .orElseThrow(() -> new ChatNotFoundException(chatUUID));
        chatRepository.delete(chat);
    }
}
