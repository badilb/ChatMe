package com.chatme.chatmeapp.models.dto;

import com.chatme.chatmeapp.models.entity.Message;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Long id;
    private String content;
    private UserEntityDTO sender;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.sender = new UserEntityDTO(message.getSender());
        this.createdAt = message.getCreatedAt();
        this.updatedAt = message.getUpdatedAt();
    }
}
