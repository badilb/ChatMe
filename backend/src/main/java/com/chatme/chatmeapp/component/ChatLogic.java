package com.chatme.chatmeapp.component;

import com.chatme.chatmeapp.models.entity.UserEntity;
import com.chatme.chatmeapp.repository.ChatRepository;
import com.chatme.chatmeapp.service.UserService;
import com.chatme.chatmeapp.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("chatValidation")
public class ChatLogic {
    private final UserService userService;

    @Autowired
    public ChatLogic(UserService userService) {
        this.userService = userService;
    }

    public boolean userOwnsTheChat(UUID chatUUID) {
        String currentSessionUsername = SessionUtil.getSessionUsername();
        UserEntity user = userService.findByUsername(currentSessionUsername);
        return user.getChats().stream()
                .anyMatch(chat -> chat.getChatUUID().equals(chatUUID));
    }

    public boolean hasUserChatWithThisUsername(String requiredUsername) {
        String currentSessionUsername = SessionUtil.getSessionUsername();
        UserEntity user = userService.findByUsername(currentSessionUsername);

        UserEntity requiredUser = userService.findByUsername(requiredUsername);
        return user.getChats().stream()
                .anyMatch(chat -> chat.getUsers().contains(requiredUser));
    }
}
