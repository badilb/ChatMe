package com.chatme.chatmeapp.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("The user does not exist. Username: " + username);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
