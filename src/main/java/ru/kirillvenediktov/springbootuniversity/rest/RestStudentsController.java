package ru.kirillvenediktov.springbootuniversity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.exceptions.ChangesNotMadeException;
import ru.kirillvenediktov.springbootuniversity.service.StudentsService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestStudentsController {

    private final StudentsService studentsService;

    @Autowired
    public RestStudentsController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping("/students")
    public List<StudentDTO> getStudents() {
        return studentsService.getAllStudentsDTO();
    }

    @GetMapping("/students/{id}")
    public StudentDTO getStudent(@PathVariable Long id) {
        return studentsService.getStudentDTO(id);
    }

    @RequestMapping(value = "/students", method = {RequestMethod.POST, RequestMethod.PUT})
    public StudentDTO saveStudent(@RequestBody StudentDTO studentDTO) {
        studentsService.saveStudent(studentDTO);
        return studentDTO;
    }

    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        if (studentsService.getStudent(id).getId() == null) {
            throw new ChangesNotMadeException("there is no student with id = " + id + " in database");
        }
        studentsService.deleteStudent(id);
        return "Student with id = " + id + " was deleted";
    }

}
