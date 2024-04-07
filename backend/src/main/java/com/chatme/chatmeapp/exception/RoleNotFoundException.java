package com.chatme.chatmeapp.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String role) {
        super("The role does not exist. Role name: " + role);
    }

    public RoleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
