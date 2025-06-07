package co.simplon.personalities.exceptions;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(Long questionId) {
        super(String.format("Question with id %s not found", questionId));
    }
}
