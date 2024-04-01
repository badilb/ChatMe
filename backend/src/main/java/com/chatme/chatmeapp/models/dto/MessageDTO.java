package com.chatme.chatmeapp.models.dto;

import com.chatme.chatmeapp.models.entity.Chat;
import com.chatme.chatmeapp.models.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Long id;
    private ChatDTO chat;
    private UserEntityDTO sender;
    private UserEntityDTO recipient;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
