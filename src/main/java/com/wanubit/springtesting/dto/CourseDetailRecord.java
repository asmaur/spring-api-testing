package com.wanubit.springtesting.dto;

import com.wanubit.springtesting.domain.CourseType;

import java.time.Instant;
import java.util.List;

public record CourseDetailRecord(
        Long id,
        String title,
        int duration,
        CourseType type,
        boolean open,
        Instant createdDate,
        List<ReviewResponseRecord> reviews
) {
}
