package com.wanubit.springtesting.service.impl;

import com.wanubit.springtesting.domain.Course;
import com.wanubit.springtesting.exceptions.CourseNotFoundException;
import com.wanubit.springtesting.repository.CourseRepository;
import com.wanubit.springtesting.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Override
    public Page<Course> list(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return repository.findAll(pageable);
    }

    @Override
    public Course create(Course course) {
        return repository.save(course);
    }

    @Override
    public Optional<Course> retrieve(Long id) {
        return repository.findById(id);
    }

    @Override
    public Course update(Course course) {
        return repository.save(course);
    }

    @Override
    public void delete(Long id) {
        Course course = repository.findById(id).orElseThrow(
                () -> new CourseNotFoundException("Course not found."));
        repository.delete(course);
    }
}
