package com.wanubit.springtesting.controller;

import com.wanubit.springtesting.domain.Course;
import com.wanubit.springtesting.dto.CourseDetailRecord;
import com.wanubit.springtesting.dto.CourseRequestRecord;
import com.wanubit.springtesting.dto.CourseResponse;
import com.wanubit.springtesting.dto.CourseResponseRecord;
import com.wanubit.springtesting.exceptions.CourseNotFoundException;
import com.wanubit.springtesting.mapper.CourseMapper;
import com.wanubit.springtesting.mapper.ReviewMapper;
import com.wanubit.springtesting.service.CourseService;
import com.wanubit.springtesting.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/courses")
public class CourseController {
    @Autowired
    CourseService service;

    @Autowired
    ReviewService reviewService;

    @GetMapping
    public ResponseEntity<CourseResponse> getAllCourses(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        Page<Course> courses = service.list(pageNo, pageSize);
        List<Course> listOfCourse = courses.getContent();

        List<CourseResponseRecord> content = listOfCourse.stream()
                .map(CourseMapper::toResponseDTO).toList();

        CourseResponse courseResponse = CourseResponse.builder()
                .content(content)
                .pageNo(courses.getNumber())
                .pageSize(courses.getSize())
                .totalElements(courses.getTotalElements())
                .totalPages(courses.getTotalPages())
                .last(courses.isLast())
                .build();
        return new ResponseEntity<>(courseResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailRecord> getCourse(@PathVariable Long id){
        try{
            var reviews = reviewService.findReviewByCourseId(id)
                    .stream().map(ReviewMapper::toResponseDTO).toList();
            var course = service.retrieve(id).orElseThrow(
                    () -> new CourseNotFoundException("Course not found!"));
            return new ResponseEntity<>(
                    CourseMapper.toResponseDetailDTO(course, reviews),
                    HttpStatus.OK
            );
        } catch (CourseNotFoundException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found", ex);
        }
    }

    @PostMapping
    public ResponseEntity<CourseResponseRecord> createCourse(@Valid @RequestBody CourseRequestRecord dto){
        var new_course = service.create(CourseMapper.toNewEntity(dto));
        return new ResponseEntity<>(CourseMapper.toResponseDTO(new_course), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CourseResponseRecord> updateCourse(@Valid @RequestBody CourseResponseRecord dto){
        var updated_course = service.create(CourseMapper.toExistingEntity(dto));
        return new ResponseEntity<>(CourseMapper.toResponseDTO(updated_course), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        service.delete(id);
        return new ResponseEntity<>("Course deleted", HttpStatus.NO_CONTENT);
    }

}

























