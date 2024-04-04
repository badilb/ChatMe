package com.chatme.chatmeapp.controller.http.v1;

import com.chatme.chatmeapp.models.dto.ChatDTO;
import com.chatme.chatmeapp.models.entity.Chat;
import com.chatme.chatmeapp.models.entity.UserEntity;
import com.chatme.chatmeapp.service.ChatService;
import com.chatme.chatmeapp.service.UserService;
import com.chatme.chatmeapp.utils.SessionUtil;
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

//    localhost:8080/api/chats/294943-53252387--523752389-53752389
    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteByChatUUID(@PathVariable(value = "uuid") UUID uuid) {
        chatService.deleteByChatUUID(uuid);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("Chat was successfully deleted");
    }

    @PostMapping
    public ResponseEntity<String> createChatDTOByUUID(@RequestParam(value = "memberUsername") String memberUsername) {
        String currentSessionUsername = SessionUtil.getSessionUsername();

        assert currentSessionUsername != null;
        if (currentSessionUsername.equals(memberUsername)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("You can't create chat for yourself");
        }

        chatService.createChat(currentSessionUsername, memberUsername);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("OK!");
    }
}
