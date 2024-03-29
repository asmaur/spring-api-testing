package com.wanubit.springtesting.repository;

import com.wanubit.springtesting.domain.Course;
import com.wanubit.springtesting.domain.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCourseByType(CourseType type);
}
