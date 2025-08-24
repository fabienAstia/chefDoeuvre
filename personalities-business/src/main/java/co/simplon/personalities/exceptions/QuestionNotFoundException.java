package co.simplon.personalities.exceptions;

import java.util.Set;

public class QuestionNotFoundException extends RuntimeException {

    public QuestionNotFoundException(Long questionId) {
        super(String.format("Question with id %s not found", questionId));
    }

    public QuestionNotFoundException(Set<Long> questionIds) {
        super(String.format("Question with ids: %s not found", questionIds));
    }
}
