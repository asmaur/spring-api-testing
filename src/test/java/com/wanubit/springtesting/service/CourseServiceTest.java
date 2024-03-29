package com.wanubit.springtesting.service;

import com.wanubit.springtesting.domain.Course;
import com.wanubit.springtesting.domain.CourseType;
import com.wanubit.springtesting.repository.CourseRepository;
import com.wanubit.springtesting.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @Mock
    private CourseRepository repository;

    @InjectMocks
    private CourseServiceImpl service;

    @Captor
    private ArgumentCaptor<Course> courseArgumentCaptor;

    private Course course;

    @BeforeEach
    void setUp() {
        course = Course.builder()
                .id(1L)
                .title("title")
                .duration(20)
                .open(true)
                .type(CourseType.INITIAL)
                .build();
    }

    @Test
    void testList() {
        Page<Course> courses = mock(Page.class);

        when(repository.findAll(any(Pageable.class))).thenReturn(courses);

        Page<Course> expectedResponse = service.list(0, 10);

        Assertions.assertNotNull(expectedResponse);
        verify(repository, times(1)).findAll(
                PageRequest.of(0, 10)
        );
    }

    @Test
    void testCreate() {
        when(repository.save(any(Course.class))).thenReturn(course);

        service.create(course);

        verify(repository, times(1)).save(courseArgumentCaptor.capture());

        Assertions.assertEquals(course.getId(), courseArgumentCaptor.getValue().getId());
        Assertions.assertEquals(course.getType(), courseArgumentCaptor.getValue().getType());
    }

    @Test
    void testRetrieve() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(course));

        var expectedCourse = service.retrieve(course.getId())
                .orElse(null);

        verify(repository, times(1)).findById(anyLong());
        Assertions.assertNotNull(expectedCourse);
        Assertions.assertEquals(course.getId(), expectedCourse.getId());
    }

    @Test
    void testUpdate() {
    }

    @Test
    void testDelete() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(course));
        doNothing().when(repository).delete(any(Course.class));

        service.delete(course.getId());

        verify(repository, times(1)).delete(course);

    }
}