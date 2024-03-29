package com.wanubit.springtesting.repository;

import com.wanubit.springtesting.domain.Course;
import com.wanubit.springtesting.domain.CourseType;
import com.wanubit.springtesting.domain.Review;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ReviewRepositoryTest {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    ReviewRepository repository;

    private Course course;
    private Course savedCourse;
    private Review review;
    private Review review2;

    @BeforeEach
    void setUp() {
        course = Course.builder()
                .title("title")
                .duration(20)
                .open(true)
                .type(CourseType.INITIAL)
                .build();
        savedCourse = courseRepository.save(course);

        review = Review.builder()
                .content("review")
                .stars(3)
                .course(savedCourse)
                .build();
        review2 = Review.builder()
                .content("review2")
                .stars(5)
                .course(savedCourse)
                .build();
    }

    @Test
    void testSaveReview(){
        //act
        Review savedReview = repository.save(review2);

        // assert
        Assertions.assertNotNull(savedReview);
        Assertions.assertTrue(savedReview.getId() > 0);
        Assertions.assertEquals(course.getId(), savedReview.getCourse().getId());
    }

    @Test
    void testFindAllReviewByCourseId() {
        //act
        repository.save(review);
        repository.save(review2);
        List<Review> reviews = repository.findAllReviewByCourseId(course.getId());

        //assert
        Assertions.assertNotNull(reviews);
        Assertions.assertEquals(2, reviews.size());
    }

    @Test
    void testFindReviewById(){
        //act
        var savedReview = repository.save(review);
        var returnedReview = repository.findById(savedReview.getId()).orElse(null);

        //assert
        Assertions.assertNotNull(returnedReview);
        Assertions.assertEquals(savedReview.getId(), returnedReview.getId());
    }

    @Test
    void testUpdateReview(){
        //arrange
        var savedReview = repository.save(review);

        //act
        savedReview.setContent("new content");
        savedReview.setStars(5);
        var updatedReview = repository.save(savedReview);

        //assert
        Assertions.assertNotNull(updatedReview);
        Assertions.assertEquals(savedReview.getId(), updatedReview.getId());
        Assertions.assertFalse(updatedReview.getStars() != review.getStars());
    }

    @Test
    void testDeleteReview(){
        //arrange
        var savedReview = repository.save(review);

        // act
        repository.delete(savedReview);
        Optional<Review> returnReview = repository.findById(savedReview.getId());

        //assert
        Assertions.assertTrue(returnReview.isEmpty());
    }
}