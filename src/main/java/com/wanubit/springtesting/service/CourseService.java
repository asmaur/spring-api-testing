package com.wanubit.springtesting.service;

import com.wanubit.springtesting.domain.Course;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CourseService {
    Page<Course> list(int pageNo, int pageSize);
    Course create(Course course);
    Optional<Course> retrieve(Long id);
    Course update(Course course);
    void delete(Long id);
}
