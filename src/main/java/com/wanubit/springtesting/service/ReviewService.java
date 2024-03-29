package com.wanubit.springtesting.service;

import com.wanubit.springtesting.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    Review create(Review review);
    Optional<Review> retrieve(Long id);
    List<Review> findReviewByCourseId(Long id);
    Review update(Review review);
    void delete(Long id);
}
