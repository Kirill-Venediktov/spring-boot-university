package ru.kirillvenediktov.springbootuniversity.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Student;
import ru.kirillvenediktov.springbootuniversity.testConfig.TestDBConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:databaseTesting/testDB.properties")
public class CoursesDAOTest {

    private static final String CREATION_SCRYPT_PATH = "databaseTesting/TestTableCreationScrypt.sql";
    private static final String FILLING_SCRYPT_PATH = "databaseTesting/testTableFillingScrypt.sql";
    private static final long EXISTED_STUDENT_ID = 5L;
    private static final long EXISTED_COURSE_ID = 4L;

    private static ApplicationContext context;
    private static DataSource dataSource;
    private CoursesDAO coursesDAO;

    @BeforeAll
    public static void startInit() {
        context = new AnnotationConfigApplicationContext(TestDBConfig.class);
        dataSource = context.getBean("dataSource", DataSource.class);
    }

    @BeforeEach
    public void init() {
        coursesDAO = context.getBean("coursesDAO", CoursesDAO.class);
        runScrypt(CREATION_SCRYPT_PATH);
        runScrypt(FILLING_SCRYPT_PATH);
    }

    public void runScrypt(String path) {
        Resource resource = new ClassPathResource(path);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
    }

    @Test
    void getStudentCourses_shouldReturnListOfStudents_whenInputIsStudent() {
        Set<Course> expectedResult = Collections.singleton(new Course(
            3L, "CHEMISTRY", "zxcvb"));
        Student student = new Student(EXISTED_STUDENT_ID, "Dmitri", "Medvedev");
        student.setCourses(expectedResult);
        Set<Course> actualResult = coursesDAO.getStudentCourses(student);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getStudentsOfCourse_shouldReturnListOfStudents_whenInputIsCourse() {
        Set<Student> expectedResult = Collections.singleton(new Student(3L, "Sergey", "Sidorov"));
        Course course = new Course(EXISTED_COURSE_ID, "MUSIC", "ggfgtv");
        course.setStudents(expectedResult);
        Set<Student> actualResult = coursesDAO.getStudentsOfCourse(course);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllCourses_shouldReturnListOfCourses() {
        List<Course> expectedResult = new ArrayList<>();
        expectedResult.add(new Course(1L, "MATH", "qwerty"));
        expectedResult.add(new Course(2L, "PHYSICS", "asdfg"));
        expectedResult.add(new Course(3L, "CHEMISTRY", "zxcvb"));
        expectedResult.add(new Course(4L, "MUSIC", "ggfgtv"));
        List<Course> actualResult = coursesDAO.getAllCourses();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getCourse_shouldReturnCourse_whenInputIsCourseId() {
        Course expectedResult = new Course(4L, "MUSIC", "ggfgtv");
        Course actualResult = coursesDAO.getCourse(EXISTED_COURSE_ID);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateCourse_shouldReturnNumberOfChangedStrings_whenInputIsExistedCourse() {
        Course expectedResult = new Course(1L, "PHYSICS", "YTREWQ");
        Course actualResult = coursesDAO.updateCourse(new Course(1L, "PHYSICS", "YTREWQ"));
        assertEquals(expectedResult, actualResult);
    }

}
