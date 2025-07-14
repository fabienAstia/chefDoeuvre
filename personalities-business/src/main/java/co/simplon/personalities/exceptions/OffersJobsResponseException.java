package co.simplon.personalities.exceptions;

public class OffersJobsResponseException extends RuntimeException {

    public OffersJobsResponseException(String message, Exception cause) {
        super(message, cause);
    }

    public OffersJobsResponseException(String message) {
        super(message);
    }
}
