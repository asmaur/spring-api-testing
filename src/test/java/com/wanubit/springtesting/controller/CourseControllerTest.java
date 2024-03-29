package com.wanubit.springtesting.controller;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanubit.springtesting.domain.Course;
import com.wanubit.springtesting.domain.CourseType;
import com.wanubit.springtesting.domain.Review;
import com.wanubit.springtesting.dto.CourseRequestRecord;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = CourseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @MockBean
    private CourseService courseService;

    @MockBean
    private ReviewService reviewService;

    @InjectMocks
    private CourseController courseController;

    private Course course;
    private Review review;


    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = standaloneSetup(courseController).build();
        course = Course.builder()
                .id(1L)
                .title("title")
                .duration(20)
                .type(CourseType.INITIAL)
                .open(true)
                .build();
        review = Review.builder()
                .id(1L)
                .content("test review")
                .stars(4)
                .course(course)
                .build();
    }

    @Test
    public void getAllCourses_ShouldReturnCourses() throws Exception {
        // Arrange
        PageImpl<Course> page = new PageImpl<>(List.of(new Course(), new Course()));
        when(courseService.list(anyInt(), anyInt())).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/api/v1/courses")
                        .param("pageNo", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(page.getTotalElements()));

        verify(courseService).list(0, 10);
    }
    @Test
    public void getCourse_WhenFound_ShouldReturnCourse() throws Exception {
        // Arrange
        Long courseId = 1L;
        when(courseService.retrieve(courseId)).thenReturn(Optional.ofNullable(course));
        when(reviewService.findReviewByCourseId(courseId)).thenReturn(List.of(review));

        // Act & Assert
        mockMvc.perform(get("/api/v1/courses/{id}", courseId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(courseService).retrieve(courseId);
        verify(reviewService).findReviewByCourseId(courseId);
    }

    @Test
    public void getCourse_WhenNotFound_ShouldReturnNotFound() throws Exception {
        // Arrange
        Long courseId = 1L;
        when(courseService.retrieve(courseId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/v1/courses/{id}", courseId))
                .andExpect(status().isNotFound());

        verify(courseService).retrieve(courseId);
    }
    @Test
    public void createCourse_ShouldCreateCourse() throws Exception {
        // Arrange
        CourseRequestRecord dto = new CourseRequestRecord("New Course", 25, CourseType.BOOTCAMP, true);
        Course course = new Course();
        when(courseService.create(any(Course.class))).thenReturn(course);

        // Act & Assert
        mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(courseService).create(any(Course.class));
    }
    @Test
    public void updateCourse_ShouldUpdateCourse() throws Exception {
        // Similar approach to createCourse, ensuring it returns 200 OK status.
    }
    @Test
    public void deleteCourse_ShouldDeleteCourse() throws Exception {
        // Arrange
        Long courseId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/v1/courses/{id}", courseId))
                .andExpect(status().isNoContent());

        verify(courseService).delete(courseId);
    }

}
