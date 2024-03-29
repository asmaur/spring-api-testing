package com.wanubit.springtesting.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.wanubit.springtesting.domain.Review;
import com.wanubit.springtesting.exceptions.ReviewNotFoundException;
import com.wanubit.springtesting.exceptions.SwearWordException;
import com.wanubit.springtesting.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceImplTest {

    @Mock
    private ReviewRepository repository;

    @InjectMocks
    private ReviewServiceImpl service;

    private Review review;

    @BeforeEach
    void setUp() {
        review = new Review();
        review.setId(1L);
        review.setContent("Ótimo curso");
    }

    @Test
    void createReviewWithSwearWords_ThrowsSwearWordException() {
        Review badReview = new Review();
        badReview.setContent("Este curso é uma merda");

        assertThrows(SwearWordException.class, () -> service.create(badReview));
    }

    @Test
    void createValidReview_ReturnsReview() {
        when(repository.save(any(Review.class))).thenReturn(review);

        Review savedReview = service.create(review);

        assertNotNull(savedReview);
        verify(repository).save(review);
    }

    @Test
    void retrieveExistingReview_ReturnsReview() {
        when(repository.findById(1L)).thenReturn(Optional.of(review));

        Optional<Review> foundReview = service.retrieve(1L);

        assertTrue(foundReview.isPresent());
        assertEquals(review.getId(), foundReview.get().getId());
    }

    @Test
    void retrieveNonExistingReview_ReturnsEmpty() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Review> foundReview = service.retrieve(999L);

        assertFalse(foundReview.isPresent());
    }

    @Test
    void deleteExistingReview_DeletesReview() {
        when(repository.findById(1L)).thenReturn(Optional.of(review));
        doNothing().when(repository).delete(any(Review.class));

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository).delete(review);
    }

    @Test
    void deleteNonExistingReview_ThrowsException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ReviewNotFoundException.class, () -> service.delete(999L));
    }


}
