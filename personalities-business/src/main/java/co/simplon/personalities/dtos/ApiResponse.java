package co.simplon.personalities.dtos;

public record ApiResponse<T>(
        String status,
        String message,
        T data,
        Object metadata
) {
}
