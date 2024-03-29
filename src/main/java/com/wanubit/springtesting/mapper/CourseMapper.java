package com.wanubit.springtesting.mapper;

import com.wanubit.springtesting.domain.Course;
import com.wanubit.springtesting.dto.CourseDetailRecord;
import com.wanubit.springtesting.dto.CourseRequestRecord;
import com.wanubit.springtesting.dto.CourseResponseRecord;
import com.wanubit.springtesting.dto.ReviewResponseRecord;

import java.util.List;

public class CourseMapper {
    public static CourseResponseRecord toResponseDTO(Course course){
        return new CourseResponseRecord(
                course.getId(),
                course.getTitle(),
                course.getDuration(),
                course.getType(),
                course.isOpen(),
                course.getCreatedDate()
        );
    }

    public static CourseDetailRecord toResponseDetailDTO(Course course, List<ReviewResponseRecord> reviews){
        return new CourseDetailRecord(
                course.getId(),
                course.getTitle(),
                course.getDuration(),
                course.getType(),
                course.isOpen(),
                course.getCreatedDate(),
                reviews
        );
    }

    public static Course toExistingEntity(CourseResponseRecord dto){
        return Course.builder()
                .id(dto.id())
                .title(dto.title())
                .duration(dto.duration())
                .open(dto.open())
                .type(dto.type())
                .build();
    }

    public static Course toNewEntity(CourseRequestRecord dto){
        return Course.builder()
                .title(dto.title())
                .duration(dto.duration())
                .open(dto.open())
                .type(dto.type())
                .build();
    }
}
