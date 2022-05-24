package ru.kirillvenediktov.springbootuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kirillvenediktov.springbootuniversity.dto.CourseDTO;
import ru.kirillvenediktov.springbootuniversity.dto.GroupDTO;
import ru.kirillvenediktov.springbootuniversity.dto.GroupWithStudentCount;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.models.Student;
import ru.kirillvenediktov.springbootuniversity.repositories.GroupRepository;

import java.util.HashSet;
import java.util.Optional;

@Service
public class DTOService {

    private final GroupRepository groupRepository;

    @Autowired
    public DTOService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public StudentDTO getStudentDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        if (student.getGroup() != null) {
            studentDTO.setGroupId(student.getGroup().getId());
        } else {
            studentDTO.setGroupId(0L);
        }
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        return studentDTO;
    }

    public GroupDTO getGroupDTO(Group group) {
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setId(group.getId());
        groupDTO.setGroupName(group.getGroupName());
        return groupDTO;
    }

    public GroupWithStudentCount getGroupWithStudentCountDTO(Group group) {
        GroupWithStudentCount groupWithStudentCount = new GroupWithStudentCount();
        groupWithStudentCount.setId(group.getId());
        groupWithStudentCount.setGroupName(group.getGroupName());
        groupWithStudentCount.setStudentCount(group.getStudents().size());
        return groupWithStudentCount;
    }

    public CourseDTO getCourseDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setCourseName(course.getCourseName());
        courseDTO.setCourseDescription(course.getCourseDescription());
        return courseDTO;
    }

    public Student getStudent(StudentDTO studentDTO) {
        Student student = new Student();
        if (studentDTO.getId() != null) {
            student.setId(studentDTO.getId());
        }
        Optional<Group> optionalGroup = groupRepository.findById(studentDTO.getGroupId());
        Group group = new Group();
        if (optionalGroup.isPresent()) {
            group = optionalGroup.get();
        }
        student.setGroup(group);
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setCourses(new HashSet<>());
        return student;
    }

    public Group getGroup(GroupDTO groupDTO) {
        Group group = new Group();
        if (groupDTO.getId() != null) {
            group.setId(groupDTO.getId());
        }
        group.setGroupName(groupDTO.getGroupName());
        return group;
    }

    public Course getCourse(CourseDTO courseDTO) {
        Course course = new Course();
        if (courseDTO.getId() != null) {
            course.setId(courseDTO.getId());
        }
        course.setCourseName(courseDTO.getCourseName());
        course.setCourseDescription(courseDTO.getCourseDescription());
        return course;
    }

}
