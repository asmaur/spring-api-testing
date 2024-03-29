package com.wanubit.springtesting.mapper;

import com.wanubit.springtesting.domain.Course;
import com.wanubit.springtesting.domain.Review;
import com.wanubit.springtesting.dto.ReviewRequestRecord;
import com.wanubit.springtesting.dto.ReviewResponseRecord;

public class ReviewMapper {
    public static ReviewResponseRecord toResponseDTO(Review review){
        return new ReviewResponseRecord(
                review.getId(),
                review.getContent(),
                review.getStars(),
                review.getCourse().getId()
        );
    }

    public static Review toExistingEntity(ReviewResponseRecord dto, Course course){
        return Review.builder()
                .id(dto.id())
                .content(dto.content())
                .stars(dto.stars())
                .course(course)
                .build();
    }

    public static Review toNewEntity(ReviewRequestRecord dto, Course course){
        return Review.builder()
                .content(dto.content())
                .stars(dto.stars())
                .course(course)
                .build();
    }
}
