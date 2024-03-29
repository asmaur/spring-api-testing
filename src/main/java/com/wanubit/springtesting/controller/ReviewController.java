package com.wanubit.springtesting.controller;


import com.wanubit.springtesting.domain.Review;
import com.wanubit.springtesting.dto.ReviewRequestRecord;
import com.wanubit.springtesting.dto.ReviewResponseRecord;
import com.wanubit.springtesting.exceptions.CourseNotFoundException;
import com.wanubit.springtesting.exceptions.ReviewNotFoundException;
import com.wanubit.springtesting.exceptions.SwearWordException;
import com.wanubit.springtesting.mapper.ReviewMapper;
import com.wanubit.springtesting.service.CourseService;
import com.wanubit.springtesting.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewController {
    @Autowired
    ReviewService service;
    @Autowired
    CourseService courseService;

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponseRecord> getReview(@PathVariable Long id){
        try {
            Review review = service.retrieve(id).orElseThrow(
                    () -> new ReviewNotFoundException("Review not found."));
            return new ResponseEntity<>(ReviewMapper.toResponseDTO(review), HttpStatus.OK);
        } catch (ReviewNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found", ex);
        }
    }

    @PostMapping
    public ResponseEntity<ReviewResponseRecord> createReview(@Valid @RequestBody ReviewRequestRecord dto){
        try{
            var course = courseService.retrieve(dto.courseID())
                    .orElseThrow(()-> new CourseNotFoundException("Course not found"));
            var newReview = service.create(ReviewMapper.toNewEntity(dto, course));
            return new ResponseEntity<>(ReviewMapper.toResponseDTO(newReview), HttpStatus.CREATED);
        } catch (CourseNotFoundException | SwearWordException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        }
    }

    @PutMapping
    public ResponseEntity<ReviewResponseRecord> updateReview(@RequestBody ReviewResponseRecord dto){
        try {
            var course = courseService.retrieve(dto.courseID())
                    .orElseThrow(() -> new CourseNotFoundException("Course not found."));
            var review = service.update(ReviewMapper.toExistingEntity(dto, course));
            return new ResponseEntity<>(ReviewMapper.toResponseDTO(review), HttpStatus.OK);
        } catch (CourseNotFoundException | SwearWordException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>("Review deleted", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<List<ReviewResponseRecord>> getCourseReviews(@PathVariable Long id){
        var reviews = service.findReviewByCourseId(id);
        return new ResponseEntity<>(
                reviews.stream().map(ReviewMapper::toResponseDTO)
                        .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

}

















