package co.simplon.personalities.exceptions;

public class TokenRetrievalException extends RuntimeException {

    public TokenRetrievalException(String message, Exception cause) {
        super(message, cause);
    }

    public TokenRetrievalException(String message) {
    }
}

