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
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.models.Student;
import ru.kirillvenediktov.springbootuniversity.repositories.GroupRepository;
import ru.kirillvenediktov.springbootuniversity.repositories.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentsService {

    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final DTOService dtoService;

    @Autowired
    public StudentsService(StudentRepository studentRepository, GroupRepository groupRepository,
                           DTOService dtoService) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.dtoService = dtoService;
    }

    @Transactional
    public Student getStudent(Long studentId) {
        Optional<Student> optionalStudent = studentRepository.findById(studentId);
        Student student = new Student();
        if (optionalStudent.isPresent()) {
            student = optionalStudent.get();
        }
        return student;
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = getStudent(studentId);
        studentRepository.delete(student);
    }

    @Transactional
    public List<CourseDTO> getStudentCourses(Long studentId) {
        Student student = getStudent(studentId);
        Set<Course> courses = student.getCourses();
        List<CourseDTO> courseDTOS = new ArrayList<>();
        if (courses != null) {
            for (Course course : courses) {
                courseDTOS.add(dtoService.getCourseDTO(course));
            }
        }
        return courseDTOS;
    }

    @Transactional
    public List<StudentDTO> getAllStudentsDTO() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(dtoService.getStudentDTO(student));
        }
        return studentDTOS;
    }

    @Transactional
    public Page<StudentDTO> getStudentPage(Pageable pageable) {
        Page<Student> students = studentRepository.findAll(pageable);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(dtoService.getStudentDTO(student));
        }
        return new PageImpl<>(studentDTOS, pageable, studentRepository.findAll().size());
    }

    @Transactional
    public StudentDTO getStudentDTO(Long studentId) {
        Student student = getStudent(studentId);
        return dtoService.getStudentDTO(student);
    }

    @Transactional
    public void saveStudent(StudentDTO studentDTO) {
        Student student;
        if (studentDTO.getId() == null) {
            student = dtoService.getStudent(studentDTO);
            studentRepository.save(student);
        } else {
            student = getStudent(studentDTO.getId());
            student.setFirstName(studentDTO.getFirstName());
            student.setLastName(studentDTO.getLastName());
            Optional<Group> groupOptional = groupRepository.findById(studentDTO.getGroupId());
            groupOptional.ifPresent(student::setGroup);
        }
    }

}
