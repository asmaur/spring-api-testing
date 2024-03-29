package com.wanubit.springtesting.dto;

import com.wanubit.springtesting.domain.CourseType;

import java.time.Instant;

public record CourseResponseRecord(
        Long id,
        String title,
        int duration,
        CourseType type,
        boolean open,
        Instant createdDate
) {
}
