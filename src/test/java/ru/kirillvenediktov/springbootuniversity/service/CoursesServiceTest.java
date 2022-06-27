package ru.kirillvenediktov.springbootuniversity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kirillvenediktov.springbootuniversity.dto.CourseDTO;
import ru.kirillvenediktov.springbootuniversity.repositories.CourseRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CoursesServiceTest {

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private StudentsService studentsService;

    @MockBean
    private DTOService dtoService;

    private CoursesService coursesService;

    @BeforeEach
    public void init() {
        coursesService = new CoursesService(courseRepository, studentsService, dtoService);
    }

    @Test
    void getStudentsOfCourse_shouldReturnListOfCoursesDTO() {
        assertNotNull(coursesService.getStudentsOfCourse(Mockito.any()));
    }

    @Test
    void getAllCourses_shouldReturnListOfCoursesDTO() {
        assertNotNull(coursesService.getAllCoursesDTO());
    }

    @Test
    void getCourseDTO_shouldReturnCourseDTO_whenInputIsCourseId() {
        Mockito.when(dtoService.getCourseDTO(Mockito.any())).thenReturn(new CourseDTO());
        assertNotNull(coursesService.getCourseDTO(Mockito.anyLong()));
    }

}
