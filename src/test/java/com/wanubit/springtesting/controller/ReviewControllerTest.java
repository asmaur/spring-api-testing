package com.wanubit.springtesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanubit.springtesting.domain.Course;
import com.wanubit.springtesting.domain.CourseType;
import com.wanubit.springtesting.domain.Review;
import com.wanubit.springtesting.dto.ReviewRequestRecord;
import com.wanubit.springtesting.service.CourseService;
import com.wanubit.springtesting.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebMvcTest(controllers = ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private CourseService courseService;

    @InjectMocks
    private ReviewController reviewController;

    private ReviewRequestRecord requestRecord;
    private Review review;
    private Course course;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(reviewController).build();
        requestRecord = new ReviewRequestRecord("test review", 4, 1L);
        course = Course.builder()
                .id(1L)
                .title("course")
                .duration(30)
                .open(true)
                .type(CourseType.INITIAL)
                .build();

        review = Review.builder()
                .id(1L)
                .content("review")
                .stars(4)
                .course(course)
                .build();
    }

    @Test
    public void getReview_WhenFound_ShouldReturnReview() throws Exception {
        // Arrange
        Long reviewId = 1L;
        //Review review = new Review(); // Adapte com um construtor adequado ou setters
        given(reviewService.retrieve(reviewId)).willReturn(Optional.of(review));

        // Act & Assert
        mockMvc.perform(get("/api/v1/reviews/{id}", reviewId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(reviewService).retrieve(reviewId);
    }

    @Test
    public void getReview_WhenNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        Long reviewId = 1L;
        given(reviewService.retrieve(reviewId)).willReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/reviews/{id}", reviewId))
                .andExpect(status().isNotFound());

        verify(reviewService).retrieve(reviewId);
    }

    @Test
    public void createReview_WhenCourseExists_ShouldCreateReview() throws Exception {
        // Arrange
        ReviewRequestRecord dto = requestRecord;
        //Review review = new Review(); // Adapte com um construtor adequado ou setters
        given(courseService.retrieve(anyLong())).willReturn(Optional.of(course));
        given(reviewService.create(any(Review.class))).willReturn(review);

        // Act & Assert
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(courseService).retrieve(anyLong());
        verify(reviewService).create(any(Review.class));
    }

    @Test
    public void createReview_WhenCourseDoesNotExist_ShouldReturnBadRequest() throws Exception {
        // Arrange
        ReviewRequestRecord dto = requestRecord;
        given(courseService.retrieve(anyLong())).willReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(post("/api/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isBadRequest());

        verify(courseService).retrieve(anyLong());
        verify(reviewService, never()).create(any(Review.class));
    }

    @Test
    public void deleteReview_ShouldDeleteReview() throws Exception {
        // Arrange
        Long reviewId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/v1/reviews/{id}", reviewId))
                .andExpect(status().isNoContent());

        verify(reviewService).delete(reviewId);
    }

    @Test
    public void getCourseReviews_ShouldReturnReviewsList() throws Exception {
        // Arrange
        Long courseId = 1L;
        List<Review> reviews = List.of(review); // Adapte com construtores adequados ou setters
        given(reviewService.findReviewByCourseId(courseId)).willReturn(reviews);

        // Act & Assert
        mockMvc.perform(get("/api/v1/reviews/course/{id}", courseId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(reviews.size()));

        verify(reviewService).findReviewByCourseId(courseId);
    }

}
