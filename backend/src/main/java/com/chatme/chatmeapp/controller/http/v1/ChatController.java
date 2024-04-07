package com.chatme.chatmeapp.controller.http.v1;

import com.chatme.chatmeapp.models.dto.ChatDTO;
import com.chatme.chatmeapp.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        ChatDTO chatDTO = chatService.findDTOByChatUUID(uuid);
        return ResponseEntity.ok(chatDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createChatDTOByUUID(@RequestParam(value = "username") String requiredUsername) {
        chatService.createChat(requiredUsername);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByChatUUID(@PathVariable(value = "uuid") UUID uuid) {
        chatService.deleteByChatUUID(uuid);
    }
}
