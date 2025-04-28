package com.personalities.dtos;

public record PageMetadata(
        int size,
        int number,
        long totalElements,
        int totalPages
) {
}
