package com.mrinmoy.spring_ai.dto;

public record BookRecommendation(
        String title,
        String author,
        int publicationYear,
        String genre,
        int pageCount,
        String summary
) {
}
