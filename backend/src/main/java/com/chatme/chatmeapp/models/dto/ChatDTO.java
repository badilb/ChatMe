package com.chatme.chatmeapp.models.dto;

import com.chatme.chatmeapp.models.entity.Message;
import com.chatme.chatmeapp.models.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ChatDTO {
    private Long id;
    private UserEntity sender;
    private UserEntity receiver;
    private UUID uuidChatID;
    private List<MessageDTO> messages = new ArrayList<>();
}
