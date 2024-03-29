package com.wanubit.springtesting.repository;

import com.wanubit.springtesting.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllReviewByCourseId(Long id);
}
