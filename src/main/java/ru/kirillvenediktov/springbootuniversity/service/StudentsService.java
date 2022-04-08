package ru.kirillvenediktov.springbootuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kirillvenediktov.springbootuniversity.dao.StudentsDAO;
import ru.kirillvenediktov.springbootuniversity.dto.CourseDTO;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class StudentsService {

    private final StudentsDAO studentsDAO;
    private final DTOService dtoService;

    @Autowired
    public StudentsService(StudentsDAO studentsDAO, DTOService dtoService) {
        this.studentsDAO = studentsDAO;
        this.dtoService = dtoService;
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentsDAO.getStudent(studentId);
        studentsDAO.deleteStudent(student);
    }

    @Transactional
    public List<CourseDTO> getStudentCourses(Long studentId) {
        Student student = studentsDAO.getStudent(studentId);
        Set<Course> courses = studentsDAO.getStudentCourses(student);
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course course : courses) {
            courseDTOS.add(dtoService.getCourseDTO(course));
        }
        return courseDTOS;
    }

    @Transactional
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentsDAO.getAllStudents();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(dtoService.getStudentDTO(student));
        }
        return studentDTOS;
    }

    @Transactional
    public StudentDTO getStudent(Long studentId) {
        Student student = studentsDAO.getStudent(studentId);
        return dtoService.getStudentDTO(student);
    }

    @Transactional
    public void saveStudent(StudentDTO studentDTO) {
        Student student = dtoService.getStudent(studentDTO);
        if (studentDTO.getId() == null) {
            studentsDAO.createStudent(student);
        } else {
            studentsDAO.updateStudent(student);
        }
    }

}
