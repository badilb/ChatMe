package com.chatme.chatmeapp.controller.http.v1;

import com.chatme.chatmeapp.models.dto.MessageDTO;
import com.chatme.chatmeapp.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/chats/{uuid}/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageByID(@PathVariable(value = "uuid") UUID chatUUID,
                                                     @PathVariable(value = "id") Long messageID) {
        MessageDTO messageDTO = messageService.findDTOByID(chatUUID, messageID);
        return ResponseEntity.ok(messageDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void sendMessage(@PathVariable(value = "uuid") UUID chatUUID,
                            @RequestParam(value = "content") String content) {
        messageService.saveMessage(chatUUID, content);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMessageByID(@PathVariable(value = "uuid") UUID chatUUID,
                                  @PathVariable(value = "id") Long messageID,
                                  @RequestParam(value = "content") String content) {
        messageService.updateMessageByID(chatUUID, messageID, content);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessageByID(@PathVariable(value = "uuid") UUID chatUUID,
                                  @PathVariable(value = "id") Long messageID) {
        messageService.deleteMessageByID(chatUUID, messageID);
    }
}
