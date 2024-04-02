package com.chatme.chatmeapp.models.dto;

import com.chatme.chatmeapp.models.entity.Chat;
import com.chatme.chatmeapp.models.entity.Message;
import com.chatme.chatmeapp.models.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class ChatDTO {
    private Long id;
    private UUID chatUUID;
    private List<MessageDTO> messages;
    private List<String> users = new ArrayList<>();

    public ChatDTO(Chat chat) {
        this.id = chat.getId();
        this.chatUUID = chat.getChatUUID();
        this.messages = chat.getMessages().stream().map(MessageDTO::new).collect(Collectors.toList());
        this.users = chat.getUsers().stream().map(UserEntity::getName).collect(Collectors.toList());
    }
}
