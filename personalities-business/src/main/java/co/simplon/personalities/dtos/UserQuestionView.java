package co.simplon.personalities.dtos;

public record UserQuestionView(
        Long id,
        String label,
        Integer questionOrder,
        String psychPref
) {
}
