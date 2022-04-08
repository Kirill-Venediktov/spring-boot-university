package ru.kirillvenediktov.springbootuniversity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kirillvenediktov.springbootuniversity.enums.CourseName;
import ru.kirillvenediktov.springbootuniversity.enums.FirstName;
import ru.kirillvenediktov.springbootuniversity.enums.LastName;
import ru.kirillvenediktov.springbootuniversity.exceptions.RandomGenerationParameterException;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.models.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomGeneratorTest {

    private RandomGenerator generator;

    @BeforeEach
    public void init() {
        generator = new RandomGenerator();
    }

    @Test
    void generateStringWithRandomChars_shouldReturnStringOfChars_whenInputIsNumberOfChars() throws RandomGenerationParameterException {
        int numberOfChars = 5;
        String randomString = generator.generateStringWithRandomChars(numberOfChars);
        int actualLength = randomString.length();
        assertEquals(numberOfChars, actualLength);
    }

    @Test
    void generateStringWithRandomChars_shouldReturnStringOfChars_whenInputIs0() throws RandomGenerationParameterException {
        int numberOfChars = 0;
        String randomString = generator.generateStringWithRandomChars(numberOfChars);
        int actualLength = randomString.length();
        assertEquals(numberOfChars, actualLength);
    }

    @Test
    void generateStringWithRandomChars_shouldThrowRandomGenerationParameterException_whenInputIsNegativeNumber() {
        int numberOfChars = -5;
        assertThrows(RandomGenerationParameterException.class, () -> generator.generateStringWithRandomChars(numberOfChars));
    }

    @Test
    void generateRandomStringOfInts_shouldReturnStringOfInts_whenInputIsNumberOfInts() throws RandomGenerationParameterException {
        int numberOfInts = 5;
        String randomString = generator.generateStringWithRandomInts(numberOfInts);
        int actualLength = randomString.length();
        assertEquals(numberOfInts, actualLength);
    }

    @Test
    void generateRandomStringOfInts_shouldReturnStringOfInts_whenInputIs0() throws RandomGenerationParameterException {
        int numberOfChars = 0;
        String randomString = generator.generateStringWithRandomInts(numberOfChars);
        int actualLength = randomString.length();
        assertEquals(numberOfChars, actualLength);
    }

    @Test
    void generateRandomStringOfInts_shouldThrowRandomGenerationParameterException_whenInputIsNegativeNumber() {
        int numberOfChars = -5;
        assertThrows(RandomGenerationParameterException.class, () -> generator.generateStringWithRandomInts(numberOfChars));
    }

    @Test
    void generateGroupName_shouldReturnGroupName() {
        String groupName = generator.generateGroupName();
        int numberOfChars = 5;
        int actualLength = groupName.length();
        assertEquals(numberOfChars, actualLength);
    }

    @Test
    void generateFistName_shouldReturnNameFromFirstNameEnum() {
        String firstName = generator.generateFirstName();
        List<String> stringNames = new ArrayList<>();
        for (FirstName name : List.of(FirstName.values())) {
            stringNames.add(name.toString());
        }
        assertTrue(stringNames.contains(firstName));
    }

    @Test
    void generateLastName_shouldReturnNameFromLastNameEnum() {
        String lastName = generator.generateLastName();
        List<String> stringNames = new ArrayList<>();
        for (LastName name : List.of(LastName.values())) {
            stringNames.add(name.toString());
        }
        assertTrue(stringNames.contains(lastName));
    }

    @Test
    void generateCourseName_shouldReturnNameFromCourseNameEnum() {
        String courseName = generator.generateCourseName();
        List<String> stringNames = new ArrayList<>();
        for (CourseName name : List.of(CourseName.values())) {
            stringNames.add(name.toString());
        }
        assertTrue(stringNames.contains(courseName));
    }

    @Test
    void generateGroup_shouldReturnGroup() {
        Group group = generator.generateGroup();
        assertNotNull(group);
    }

    @Test
    void generateCourse_shouldReturnCourse() {
        Course course = generator.generateCourse();
        assertNotNull(course);
    }

    @Test
    void generateStudent_shouldReturnStudent() {
        Student student = generator.generateStudent();
        assertNotNull(student);
    }

}
