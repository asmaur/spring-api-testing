package com.wanubit.springtesting.service.impl;

import com.wanubit.springtesting.domain.Review;
import com.wanubit.springtesting.exceptions.ReviewNotFoundException;
import com.wanubit.springtesting.exceptions.SwearWordException;
import com.wanubit.springtesting.repository.ReviewRepository;
import com.wanubit.springtesting.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository repository;

    @Override
    public Review create(Review review) {
        if(containsSwearWords(review.getContent())){
            throw new SwearWordException("");
        }
        return repository.save(review);
    }

    @Override
    public Optional<Review> retrieve(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Review> findReviewByCourseId(Long id) {
        return repository.findAllReviewByCourseId(id);
    }

    @Override
    public Review update(Review review) {
        if(containsSwearWords(review.getContent())){
            throw new SwearWordException("");
        }
        return repository.save(review);
    }

    @Override
    public void delete(Long id) {
        Review review = repository.findById(id).orElseThrow(
                () -> new ReviewNotFoundException("Review not found."));
        repository.delete(review);
    }

    public boolean containsSwearWords(String comment){
        String[] words = {"merda", "puta"};
        for (String word : words){
            if(comment.contains(word)){
                throw  new SwearWordException("Review contêm palavras inadequadas.");
            }
        }
        return false;
    }

}
