package com.personalities.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super(String.format("User with Id %s is not found", username));
    }
}
