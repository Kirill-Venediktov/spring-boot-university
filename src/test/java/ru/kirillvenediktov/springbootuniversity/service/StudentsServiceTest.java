package ru.kirillvenediktov.springbootuniversity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.repositories.GroupRepository;
import ru.kirillvenediktov.springbootuniversity.repositories.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class StudentsServiceTest {

    private StudentsService studentsService;
    @MockBean
    private StudentRepository studentRepository;
    @MockBean
    private GroupRepository groupRepository;
    @MockBean
    private DTOService dtoService;

    @BeforeEach
    public void init() {
        studentsService = new StudentsService(studentRepository, groupRepository, dtoService);
    }

    @Test
    void getStudent_shouldReturnStudent_whenInputIsStudentId() {
        assertNotNull(studentsService.getStudent(Mockito.anyLong()));
    }

    @Test
    void getStudentCourses_shouldReturnListOfCoursesDTO_whenInputIsStudentId() {
        assertNotNull(studentsService.getStudentCourses(Mockito.any()));
    }

    @Test
    void getStudentDTO_shouldReturnStudentDTO() {
        Mockito.when(dtoService.getStudentDTO(Mockito.any())).thenReturn(new StudentDTO());
        assertNotNull(studentsService.getStudentDTO(Mockito.anyLong()));
    }

}
