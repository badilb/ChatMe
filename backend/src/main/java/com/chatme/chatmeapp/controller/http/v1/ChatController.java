package com.chatme.chatmeapp.controller.http.v1;

import com.chatme.chatmeapp.models.dto.ChatDTO;
import com.chatme.chatmeapp.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/chats")
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ChatDTO> findChatDTOByUUID(@PathVariable(value = "uuid") UUID uuid) {
        Optional<ChatDTO> chatDTO = chatService.findDTOByChatUUID(uuid);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(chatDTO.get());
    }
}
