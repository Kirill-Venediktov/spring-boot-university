package ru.kirillvenediktov.springbootuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kirillvenediktov.springbootuniversity.dao.CoursesDAO;
import ru.kirillvenediktov.springbootuniversity.dao.StudentsDAO;
import ru.kirillvenediktov.springbootuniversity.dto.CourseDTO;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CoursesService {

    private final CoursesDAO coursesDAO;
    private final DTOService dtoService;
    private final StudentsDAO studentsDAO;

    @Autowired
    public CoursesService(CoursesDAO coursesDAO, DTOService dtoService, StudentsDAO studentsDAO) {
        this.coursesDAO = coursesDAO;
        this.dtoService = dtoService;
        this.studentsDAO = studentsDAO;
    }

    @Transactional
    public void removeStudentFromCourse(Long studentId, Long courseId) {
        Student student = studentsDAO.getStudent(studentId);
        Course course = coursesDAO.getCourse(courseId);
        coursesDAO.removeStudentFromCourse(student, course);
    }

    @Transactional
    public void addStudentToCourse(Long studentId, Long courseId) {
        Student student = studentsDAO.getStudent(studentId);
        Course course = coursesDAO.getCourse(courseId);
        coursesDAO.addStudentToCourse(student, course);
    }

    @Transactional
    public List<StudentDTO> getStudentsOfCourse(Long courseId) {
        Course course = coursesDAO.getCourse(courseId);
        Set<Student> students = coursesDAO.getStudentsOfCourse(course);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(dtoService.getStudentDTO(student));
        }
        return studentDTOS;
    }

    @Transactional
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = coursesDAO.getAllCourses();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course course : courses) {
            courseDTOS.add(dtoService.getCourseDTO(course));
        }
        return courseDTOS;
    }

    @Transactional
    public CourseDTO getCourse(Long courseId) {
        Course course = coursesDAO.getCourse(courseId);
        return dtoService.getCourseDTO(course);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = coursesDAO.getCourse(courseId);
        coursesDAO.deleteCourse(course);
    }

    @Transactional
    public void saveCourse(CourseDTO courseDTO) {
        Course course = dtoService.getCourse(courseDTO);
        if (course.getId() == null) {
            coursesDAO.createCourse(course);
        } else {
            coursesDAO.updateCourse(course);
        }
    }

}
