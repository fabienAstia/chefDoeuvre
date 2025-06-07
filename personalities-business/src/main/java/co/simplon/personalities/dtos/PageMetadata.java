package co.simplon.personalities.dtos;

public record PageMetadata(
        int size,
        int number,
        long totalElements,
        int totalPages
) {
}
