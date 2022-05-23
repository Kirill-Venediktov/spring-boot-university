package ru.kirillvenediktov.springbootuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kirillvenediktov.springbootuniversity.dto.CourseDTO;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Student;
import ru.kirillvenediktov.springbootuniversity.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CoursesService {

    private final CourseRepository courseRepository;
    private final StudentsService studentsService;
    private final DTOService dtoService;

    @Autowired
    public CoursesService(CourseRepository courseRepository, StudentsService studentsService, DTOService dtoService) {
        this.courseRepository = courseRepository;
        this.studentsService = studentsService;
        this.dtoService = dtoService;
    }

    @Transactional
    public void removeStudentFromCourse(StudentDTO studentDTO, Long courseId) {
        Student student = studentsService.getStudent(studentDTO.getId());
        Course course = getCourse(courseId);
        student.getCourses().remove(course);
    }

    @Transactional
    public void addStudentToCourse(StudentDTO studentDTO, Long courseId) {
        Student student = studentsService.getStudent(studentDTO.getId());
        Course course = getCourse(courseId);
        student.getCourses().add(course);
    }

    @Transactional
    public List<StudentDTO> getStudentsOfCourse(Long courseId) {
        Course course = getCourse(courseId);
        Set<Student> students = course.getStudents();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        if (students != null) {
            for (Student student : students) {
                studentDTOS.add(dtoService.getStudentDTO(student));
            }
        }
        return studentDTOS;
    }

    @Transactional
    public Page<CourseDTO> getCoursesPage(Pageable pageable) {
        Page<Course> courses = courseRepository.findAll(pageable);
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course course : courses) {
            courseDTOS.add(dtoService.getCourseDTO(course));
        }
        return new PageImpl<>(courseDTOS, pageable, courseRepository.findAll().size());
    }

    @Transactional
    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        for (Course course : courses) {
            courseDTOS.add(dtoService.getCourseDTO(course));
        }
        return courseDTOS;
    }

    @Transactional
    public Course getCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        Course course = new Course();
        if (optionalCourse.isPresent()) {
            course = optionalCourse.get();
        }
        return course;
    }

    @Transactional
    public CourseDTO getCourseDTO(Long courseId) {
        Course course = getCourse(courseId);
        return dtoService.getCourseDTO(course);
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        Course course = getCourse(courseId);
        courseRepository.delete(course);
    }

    @Transactional
    public void saveCourse(CourseDTO courseDTO) {
        Course course;
        if (courseDTO.getId() == null) {
            course = dtoService.getCourse(courseDTO);
            courseRepository.save(course);
        } else {
            course = getCourse(courseDTO.getId());
            course.setCourseName(courseDTO.getCourseName());
            course.setCourseDescription(courseDTO.getCourseDescription());
        }
    }

}
