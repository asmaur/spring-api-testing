package com.wanubit.springtesting.dto;

public record ReviewResponseRecord(
        Long id,
        String content,
        int stars,
        Long courseID
) {
}
