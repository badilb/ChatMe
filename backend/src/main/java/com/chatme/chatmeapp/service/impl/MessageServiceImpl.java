package com.chatme.chatmeapp.service.impl;

import com.chatme.chatmeapp.exception.ChatNotFoundException;
import com.chatme.chatmeapp.exception.MessageNotFoundException;
import com.chatme.chatmeapp.exception.UserNotFoundException;
import com.chatme.chatmeapp.models.dto.MessageDTO;
import com.chatme.chatmeapp.models.entity.Chat;
import com.chatme.chatmeapp.models.entity.Message;
import com.chatme.chatmeapp.models.entity.UserEntity;
import com.chatme.chatmeapp.repository.ChatRepository;
import com.chatme.chatmeapp.repository.MessageRepository;
import com.chatme.chatmeapp.repository.UserRepository;
import com.chatme.chatmeapp.service.MessageService;
import com.chatme.chatmeapp.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ChatRepository chatRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("@chatValidation.userOwnsTheChat(#chatUUID)")
    public MessageDTO findDTOByID(UUID chatUUID, Long messageID) {
        return messageRepository.findDTOById(messageID)
                .orElseThrow(() -> new MessageNotFoundException(messageID));
    }

    @Override
    @Transactional
    @PreAuthorize("@chatValidation.userOwnsTheChat(#chatUUID)")
    public void saveMessage(UUID chatUUID, String content) {
        Chat chat = chatRepository.findByChatUUID(chatUUID)
                .orElseThrow(() -> new ChatNotFoundException(chatUUID));

        String currentSessionUsername = SessionUtil.getSessionUsername();
        UserEntity senderUser = userRepository.findByUsername(currentSessionUsername)
                .orElseThrow(() -> new UserNotFoundException(currentSessionUsername));

        Message message = new Message();
        message.setChat(chat);
        message.setSender(senderUser);
        message.setContent(content);
        Message savedMessage = messageRepository.saveAndFlush(message);

        chat.getMessages().add(savedMessage);
        chatRepository.save(chat);
    }

    @Override
    @PreAuthorize("@chatValidation.userOwnsTheChat(#chatUUID) && " +
                  "@messageValidation.userOwnsTheMessage(#messageID)")
    public void updateMessageByID(UUID chatUUID, Long messageID, String content) {
        Message message = messageRepository.findById(messageID)
                .orElseThrow(() -> new MessageNotFoundException(messageID));
        message.setContent(content);
        messageRepository.save(message);
    }

    @Override
    @PreAuthorize("@chatValidation.userOwnsTheChat(#chatUUID) && " +
                  "@messageValidation.userOwnsTheMessage(#messageID)")
    public void deleteMessageByID(UUID chatUUID, Long messageID) {
        Message message = messageRepository.findById(messageID)
                .orElseThrow(() -> new MessageNotFoundException(messageID));
        messageRepository.delete(message);
    }
}
