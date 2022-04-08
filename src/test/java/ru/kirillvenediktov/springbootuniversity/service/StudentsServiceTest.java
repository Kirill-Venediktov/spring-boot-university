package ru.kirillvenediktov.springbootuniversity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kirillvenediktov.springbootuniversity.dao.StudentsDAO;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.models.Student;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class StudentsServiceTest {

    private StudentsService studentsService;
    @MockBean
    private StudentsDAO studentsDAO;
    @MockBean
    private DTOService dtoService;

    @BeforeEach
    public void init() {
        studentsService = new StudentsService(studentsDAO, dtoService);
    }

    @Test
    void getStudentCourses_shouldReturnListOfCoursesDTO_whenInputIsStudentId() {
        assertNotNull(studentsService.getStudentCourses(Mockito.any()));
    }

    @Test
    void getAllStudents_shouldReturnListOfStudentsDTO() {
        assertNotNull(studentsService.getAllStudents());
    }

    @Test
    void getStudent_shouldReturnStudentDTO() {
        Mockito.when(studentsDAO.getStudent(Mockito.anyLong())).thenReturn(new Student());
        Mockito.when(dtoService.getStudentDTO(Mockito.any())).thenReturn(new StudentDTO());
        assertNotNull(studentsService.getStudent(Mockito.anyLong()));
    }

}
