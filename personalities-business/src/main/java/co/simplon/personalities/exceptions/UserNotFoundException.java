package co.simplon.personalities.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super(String.format("User with username: %s is not found", username));
    }
}
