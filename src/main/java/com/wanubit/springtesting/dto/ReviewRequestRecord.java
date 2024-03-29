package com.wanubit.springtesting.dto;

public record ReviewRequestRecord(
        String content,
        int stars,
        Long courseID
) {
}
