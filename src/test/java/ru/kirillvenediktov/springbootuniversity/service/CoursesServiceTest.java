package ru.kirillvenediktov.springbootuniversity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kirillvenediktov.springbootuniversity.dao.CoursesDAO;
import ru.kirillvenediktov.springbootuniversity.dao.StudentsDAO;
import ru.kirillvenediktov.springbootuniversity.dto.CourseDTO;
import ru.kirillvenediktov.springbootuniversity.models.Course;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CoursesServiceTest {

    @MockBean
    private CoursesDAO coursesDAO;

    @MockBean
    private DTOService dtoService;

    @MockBean
    private StudentsDAO studentsDAO;

    private CoursesService coursesService;

    @BeforeEach
    public void init() {
        coursesService = new CoursesService(coursesDAO, dtoService, studentsDAO);
    }

    @Test
    void getStudentsOfCourse_shouldReturnListOfCoursesDTO() {
        assertNotNull(coursesService.getStudentsOfCourse(Mockito.any()));
    }

    @Test
    void getAllCourses_shouldReturnListOfCoursesDTO() {
        assertNotNull(coursesService.getAllCourses());
    }

    @Test
    void getCourse_shouldReturnCourseDTO_whenInputIsCourseId() {
        Mockito.when(coursesDAO.getCourse(Mockito.anyLong())).thenReturn(new Course());
        Mockito.when(dtoService.getCourseDTO(Mockito.any())).thenReturn(new CourseDTO());
        assertNotNull(coursesService.getCourse(Mockito.anyLong()));
    }

}
