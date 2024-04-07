package com.chatme.chatmeapp.exception;

public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException(Long id) {
        super("The message does not exist. Message ID: " + id);
    }

    public MessageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
