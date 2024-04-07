package com.chatme.chatmeapp.models.dto;

import com.chatme.chatmeapp.models.entity.Role;
import com.chatme.chatmeapp.models.entity.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserEntityDTO {
    private Long id;
    private String name;
    private String surname;
    private String username;
    private List<String> roles;
    private List<String> chats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserEntityDTO(UserEntity user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.username = user.getUsername();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        this.chats = user.getChats().stream().map(chat -> chat.getChatUUID().toString()).collect(Collectors.toList());
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
