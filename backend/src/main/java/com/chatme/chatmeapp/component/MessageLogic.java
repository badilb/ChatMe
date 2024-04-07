package com.chatme.chatmeapp.component;

import com.chatme.chatmeapp.models.entity.Message;
import com.chatme.chatmeapp.models.entity.UserEntity;
import com.chatme.chatmeapp.repository.MessageRepository;
import com.chatme.chatmeapp.service.UserService;
import com.chatme.chatmeapp.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("messageValidation")
public class MessageLogic {
    private final UserService userService;
    private final MessageRepository messageRepository;

    @Autowired
    public MessageLogic(UserService userService, MessageRepository messageRepository) {
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    public boolean userOwnsTheMessage(Long messageID) {
        String currentSessionUsername = SessionUtil.getSessionUsername();
        UserEntity user = userService.findByUsername(currentSessionUsername);
        Message message = messageRepository.findById(messageID).orElse(null);

        return message != null && message.getSender().equals(user);
    }
}
