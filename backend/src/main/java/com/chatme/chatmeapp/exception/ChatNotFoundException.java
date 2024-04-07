package com.chatme.chatmeapp.exception;

import java.util.UUID;

public class ChatNotFoundException extends RuntimeException {
    public ChatNotFoundException(UUID uuid) {
        super("The chat does not exist. Chat UUID: " + uuid.toString());
    }

    public ChatNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
