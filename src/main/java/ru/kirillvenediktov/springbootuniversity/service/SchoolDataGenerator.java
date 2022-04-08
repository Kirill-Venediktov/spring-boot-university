package ru.kirillvenediktov.springbootuniversity.service;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.kirillvenediktov.springbootuniversity.enums.CourseName;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.models.Student;
import ru.kirillvenediktov.springbootuniversity.enums.LoggerConstant;

import java.util.ArrayList;
import java.util.List;

@Component
public class SchoolDataGenerator {

    private static final int QUANTITY_OF_GROUPS = 10;
    private static final int QUANTITY_OF_COURSES = 10;
    private static final int NUMBER_OF_STUDENTS = 200;
    private static final int MIN_NUMBER_OF_STUDENTS_IN_GROUP = 10;
    private static final int MAX_NUMBER_OF_STUDENTS_IN_GROUP = 30;
    private static final Logger logger = LoggerFactory.getLogger(SchoolDataGenerator.class);
    private final RandomGenerator randomGenerator = new RandomGenerator();
    @Getter
    private List<Group> groups;
    @Getter
    private List<Course> courses;
    @Getter
    private List<Student> students;

    public SchoolDataGenerator() {
        generateData();
    }

    public void generateData() {
        groups = generateGroups();
        courses = generateCourses();
        students = generateStudents();
        assignGroupToStudent(students, groups);
    }

    private List<Group> generateGroups() {
        logger.debug(LoggerConstant.GENERATING_GROUPS_LIST_INFO_STRING.getValue());
        groups = new ArrayList<>();
        for (int i = 0; i < QUANTITY_OF_GROUPS; i++) {
            Group group = randomGenerator.generateGroup();
            groups.add(group);
        }
        logger.debug(LoggerConstant.GROUP_LIST_GENERATED.getValue(), groups);
        return groups;
    }

    private List<Course> generateCourses() {
        logger.debug(LoggerConstant.GENERATING_COURSES_LIST_INFO_STRING.getValue());
        List<CourseName> courseNames = new ArrayList<>();
        courses = new ArrayList<>();
        while (courseNames.size() < QUANTITY_OF_COURSES) {
            courseNames.addAll(List.of(CourseName.values()));
        }
        for (int i = 0; i < QUANTITY_OF_COURSES; i++) {
            Course course = randomGenerator.generateCourse();
            course.setCourseName(courseNames.get(i).toString());
            courses.add(course);
        }
        logger.debug(LoggerConstant.COURSES_LIST_GENERATED.getValue(), courses);
        return courses;
    }

    private List<Student> generateStudents() {
        logger.debug(LoggerConstant.GENERATING_STUDENTS_LIST_INFO_STRING.getValue());
        students = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_STUDENTS; i++) {
            Student student = randomGenerator.generateStudent();
            students.add(student);
        }
        logger.debug(LoggerConstant.STUDENTS_LIST_GENERATED.getValue(), students);
        return students;
    }

    private void arrangeStudentsInGroups(List<Group> groups) {
        logger.debug(LoggerConstant.BEFORE_ARRANGING.getValue(), groups);
        List<Student> redundantStudents = new ArrayList<>();
        for (Group group : groups) {
            if (!redundantStudents.isEmpty()) {
                group.getStudents().addAll(redundantStudents);
                redundantStudents.clear();
            }
            if (group.getStudents().size() < MIN_NUMBER_OF_STUDENTS_IN_GROUP) {
                redundantStudents.addAll(group.getStudents());
                group.getStudents().clear();
            } else if (group.getStudents().size() > MAX_NUMBER_OF_STUDENTS_IN_GROUP) {
                redundantStudents.addAll(group.getStudents()
                    .subList(MAX_NUMBER_OF_STUDENTS_IN_GROUP + 1, group.getStudents().size()));
                group.getStudents().removeAll(redundantStudents);
            }
        }
        logger.debug(LoggerConstant.AFTER_ARRANGING.getValue(), groups);
    }

    private void registerStudentInGroup(List<Group> groups) {
        logger.debug(LoggerConstant.STUDENT_REGISTERING_INFO_STRING.getValue());
        for (Group group : groups) {
            for (Student student : group.getStudents()) {
                student.setGroup(group);
                group.getStudents().add(student);
                logger.trace(LoggerConstant.STUDENT_REGISTERED_IN_GROUP_STRING.getValue(), student, group);
            }
        }
    }

    private void assignGroupToStudent(List<Student> students, List<Group> groups) {
        randomGenerator.addStudentsToGroups(students, groups);
        arrangeStudentsInGroups(groups);
        registerStudentInGroup(groups);
    }

}
