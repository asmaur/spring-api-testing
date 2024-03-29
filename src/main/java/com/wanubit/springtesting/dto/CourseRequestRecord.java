package com.wanubit.springtesting.dto;

import com.wanubit.springtesting.domain.CourseType;

public record CourseRequestRecord(
        String title,
        int duration,
        CourseType type,
        boolean open
) {
}
