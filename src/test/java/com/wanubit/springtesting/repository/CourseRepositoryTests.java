package com.wanubit.springtesting.repository;

import com.wanubit.springtesting.domain.Course;
import com.wanubit.springtesting.domain.CourseType;
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
class CourseRepositoryTests {
    @Autowired
    private CourseRepository repository;

    private Course course;
    private Course course2;

    @BeforeEach
    void setUp() {
        course = Course.builder()
                .title("title")
                .duration(20)
                .open(true)
                .type(CourseType.BOOTCAMP)
                .build();

        course2 = Course.builder()
                .title("title 2")
                .duration(20)
                .open(true)
                .type(CourseType.BOOTCAMP)
                .build();
    }

    @Test
    void testSaveCourse(){
        //arrange

        // act
        Course savedCourse = repository.save(course);

        //assert
        Assertions.assertNotNull(savedCourse);
        Assertions.assertTrue(savedCourse.getId() > 0);
    }

    @Test
    void testGetAllCourse(){
        //arrange

        repository.save(course);
        repository.save(course2);

        //act
        List<Course> courses = repository.findAll();

        //assert
        Assertions.assertNotNull(courses);
        Assertions.assertEquals(2, courses.size());

    }

    @Test
    void testFindCourseById(){
        //arrange
        repository.save(course);
        Course savedCourse = repository.save(course2);

        // act
        Course returnedCourse = repository.findById(savedCourse.getId()).orElse(null);

        //assert
        Assertions.assertNotNull(returnedCourse);
        Assertions.assertTrue(returnedCourse.getId() > 0);
    }

    @Test
    void testUpdateCourse(){
        // arrange
        Course savedCourse = repository.save(course);

        //
        savedCourse.setOpen(false);
        savedCourse.setType(CourseType.INITIAL);
        Course updatedCourse = repository.save(savedCourse);

        // assert
        Assertions.assertNotNull(updatedCourse);
        Assertions.assertFalse(updatedCourse.isOpen());
    }

    @Test
    void testFindCourseByType() {
        //arrange
        Course savedCourse = repository.save(course);
        //act
        List<Course> courses = repository.findCourseByType(CourseType.BOOTCAMP);

        //assert
        Assertions.assertNotNull(courses);
        Assertions.assertEquals(1, courses.size());
    }

    @Test
    void testDeleteCourse(){
        //arrange
        var savedCourse = repository.save(course);

        //act
        repository.delete(savedCourse);
        Optional<Course> returnedCourse = repository.findById(savedCourse.getId());

        //assert
        Assertions.assertTrue(returnedCourse.isEmpty());
    }
}