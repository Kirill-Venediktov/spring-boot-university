package ru.kirillvenediktov.springbootuniversity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kirillvenediktov.springbootuniversity.dao.CoursesDAO;
import ru.kirillvenediktov.springbootuniversity.dao.GroupsDAO;
import ru.kirillvenediktov.springbootuniversity.dto.CourseDTO;
import ru.kirillvenediktov.springbootuniversity.dto.GroupDTO;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.models.Student;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class DTOServiceTest {

    @MockBean
    private GroupsDAO groupsDAO;

    @MockBean
    private CoursesDAO coursesDAO;

    private DTOService dtoService;

    @BeforeEach
    public void init() {
        dtoService = new DTOService(groupsDAO, coursesDAO);
    }

    @Test
    void getStudentDTO_shouldReturnStudentDTO_whenInputIsStudent() {
        assertNotNull(dtoService.getStudentDTO(new Student()));
    }

    @Test
    void getGroupDTO_shouldReturnGroupDTO_whenInputIsGroup() {
        assertNotNull(dtoService.getGroupDTO(new Group()));
    }

    @Test
    void getCourseDTO_shouldReturnCourseDTO_whenInputIsCourse() {
        assertNotNull(dtoService.getCourseDTO(new Course()));
    }

    @Test
    void getGroupWithStudentCountDTO_shouldReturnGroupWithStudentCount_whenInputIsGroup() {
        assertNotNull(dtoService.getGroupWithStudentCountDTO(new Group()));
    }

    @Test
    void getStudent_shouldReturnStudent_whenInputIsStudentDTO() {
        StudentDTO studentDTO = new StudentDTO(1L, 2L, "Ivan", "Petrov");
        assertNotNull(dtoService.getStudent(studentDTO));
    }

    @Test
    void getGroup_shouldReturnGroup_whenInputIsGroupDTO() {
        GroupDTO groupDTO = new GroupDTO(1L, "test");
        assertNotNull(dtoService.getGroup(groupDTO));
    }

    @Test
    void getCourse_shouldReturnCourse_whenInputIsCourseDTO() {
        CourseDTO courseDTO = new CourseDTO(1L, "test", "description");
        assertNotNull(dtoService.getCourse(courseDTO));
    }

}
