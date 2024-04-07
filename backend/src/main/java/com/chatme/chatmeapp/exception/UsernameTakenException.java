package com.chatme.chatmeapp.exception;

public class UsernameTakenException extends RuntimeException {
    public UsernameTakenException(String username) {
        super("Username is already taken for username: " + username);
    }

    public UsernameTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}
