package ru.kirillvenediktov.springbootuniversity.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.kirillvenediktov.springbootuniversity.enums.CourseName;
import ru.kirillvenediktov.springbootuniversity.enums.FirstName;
import ru.kirillvenediktov.springbootuniversity.enums.LastName;
import ru.kirillvenediktov.springbootuniversity.exceptions.RandomGenerationParameterException;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.models.Student;
import ru.kirillvenediktov.springbootuniversity.enums.LoggerConstant;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

@Component
public class RandomGenerator {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
    private static final int SINGLE_DIGIT_LIMIT = 10;
    private static final int NUMBER_OF_SYMBOLS = 2;
    private static final int MIN_NUMBER_OF_COURSES = 1;
    private static final int MAX_NUMBER_OF_COURSES = 3;
    private static final int NUMBER_OF_DECRYPTION_SYMBOLS = 10;
    private static final String INVALID_NUMBER_OF_SYMBOLS = "Invalid number of symbols = ";
    private static final Logger logger = LoggerFactory.getLogger(RandomGenerator.class);
    private final Random random = new Random();

    public String generateStringWithRandomChars(int numberOfSymbols) throws RandomGenerationParameterException {
        if (numberOfSymbols < 0) {
            throw new RandomGenerationParameterException(INVALID_NUMBER_OF_SYMBOLS + numberOfSymbols);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numberOfSymbols; i++) {
            builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }
        String result = builder.toString();
        logger.debug("string {} generated", result);
        return result;
    }

    public String generateStringWithRandomInts(int numberOfSymbols) throws RandomGenerationParameterException {
        if (numberOfSymbols < 0) {
            throw new RandomGenerationParameterException(INVALID_NUMBER_OF_SYMBOLS + numberOfSymbols);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numberOfSymbols; i++) {
            builder.append(random.nextInt(SINGLE_DIGIT_LIMIT));
        }
        return builder.toString();
    }

    public String generateGroupName() {
        String charsString = null;
        String intsString = null;
        try {
            charsString = generateStringWithRandomChars(NUMBER_OF_SYMBOLS);
            intsString = generateStringWithRandomInts(NUMBER_OF_SYMBOLS);
        } catch (RandomGenerationParameterException e) {
            logger.error(LoggerConstant.GROUP_NAME_GENERATION_EXCEPTION.getValue(), e);
        }
        return charsString + "-" + intsString;
    }

    public String generateFirstName() {
        List<FirstName> names = List.of(FirstName.values());
        return names.get(random.nextInt(names.size())).toString();
    }

    public String generateLastName() {
        List<LastName> lastNames = List.of(LastName.values());
        return lastNames.get(random.nextInt(lastNames.size())).toString();
    }

    public String generateCourseName() {
        List<CourseName> courseNames = List.of(CourseName.values());
        return courseNames.get(random.nextInt(courseNames.size())).toString();
    }

    public void addStudentsToGroups(List<Student> students, List<Group> groups) {
        for (Student student : students) {
            Group group = groups.get(random.nextInt(groups.size()));
            student.setGroup(group);
        }
    }

    public Group generateGroup() {
        Group group = new Group();
        group.setGroupName(generateGroupName());
        return group;
    }

    public Course generateCourse() {
        Course course = new Course();
        course.setCourseName(generateCourseName());
        try {
            String description = generateStringWithRandomChars(NUMBER_OF_DECRYPTION_SYMBOLS);
            course.setCourseDescription(description);
        } catch (RandomGenerationParameterException e) {
            logger.error(LoggerConstant.COURSE_DESCRIPTION_GENERATION_EXCEPTION.getValue(), e);
        }
        course.setStudents(new HashSet<>());
        return course;
    }

    public Student generateStudent() {
        Student student = new Student();
        student.setFirstName(generateFirstName());
        student.setLastName(generateLastName());
        student.setCourses(new HashSet<>());
        return student;
    }

    public void assignCoursesForStudents(List<Student> students, List<Course> courses) {
        for (Student student : students) {
            for (int i = 0; i < random.nextInt(
                MAX_NUMBER_OF_COURSES - MIN_NUMBER_OF_COURSES + 1) + MIN_NUMBER_OF_COURSES; i++) {
                Course course = courses.get(random.nextInt(courses.size()));
                student.addCourseToStudent(course);
                course.addStudentToCourse(student);
            }
        }
    }

}
