package ru.kirillvenediktov.springbootuniversity.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Student;
import ru.kirillvenediktov.springbootuniversity.testConfig.TestDBConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ContextConfiguration(classes = {TestDBConfig.class})
@SpringBootTest
public class StudentsDAOTest {

    private static final String CREATION_SCRYPT_PATH = "databaseTesting/TestTableCreationScrypt.sql";
    private static final String FILLING_SCRYPT_PATH = "databaseTesting/testTableFillingScrypt.sql";
    private static final long EXISTED_STUDENT_ID = 3L;

    private static ApplicationContext context;
    private static DataSource dataSource;
    private StudentsDAO studentsDAO;

    @BeforeAll
    public static void startInit() {
        context = new AnnotationConfigApplicationContext(TestDBConfig.class);
        dataSource = context.getBean("dataSource", DataSource.class);
    }

    @BeforeEach
    public void init() {
        studentsDAO = context.getBean("studentsDAO", StudentsDAO.class);
        runScrypt(CREATION_SCRYPT_PATH);
        runScrypt(FILLING_SCRYPT_PATH);
    }

    public void runScrypt(String path) {
        Resource resource = new ClassPathResource(path);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
    }

    @Test
    void getStudentCourses_shouldReturnSetOfCourses_whenInputIsStudent() {
        Set<Course> expectedResult = Collections.singleton(new Course(
            3L, "CHEMISTRY", "zxcvb"));
        Student student = new Student(4L, "Vladimir", "Putin");
        student.setCourses(expectedResult);
        Set<Course> actualResult = studentsDAO.getStudentCourses(student);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getAllStudents_shouldReturnListOfStudentsDTO() {
        List<Student> expectedResult = new ArrayList<>();
        expectedResult.add(new Student(1L, "Ivan", "Petrov"));
        expectedResult.add(new Student(2L, "Petr", "Ivanov"));
        expectedResult.add(new Student(3L, "Sergey", "Sidorov"));
        expectedResult.add(new Student(4L, "Vladimir", "Putin"));
        expectedResult.add(new Student(5L, "Dmitri", "Medvedev"));
        List<Student> actualResult = studentsDAO.getAllStudents();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getStudent_shouldReturnStudentsDTO_whenInputIsStudenId() {
        Student expectedResult = new Student(3L, "Sergey", "Sidorov");
        Student actualResult = studentsDAO.getStudent(EXISTED_STUDENT_ID);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateStudent_shouldReturnNumberOfChangedStrings_whenInputIsExistedStudent() {
        Student expectedResult = new Student(3L, "Sergey", "Ivanov");
        Student actualResult = studentsDAO.updateStudent(new Student(3L, "Sergey", "Ivanov"));
        assertEquals(expectedResult, actualResult);
    }

}
