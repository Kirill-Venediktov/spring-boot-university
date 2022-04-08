package ru.kirillvenediktov.springbootuniversity.enums;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.kirillvenediktov.springbootuniversity.enums.DBAction.CREATE_STUDENT;

class DBActionTest {

    @Test
    void fetchValue_shouldReturnEnumValue_whenInputIsIntegerValueOfIt() {
        int inputValue = 3;
        DBAction actualResult = DBAction.fetchValue(inputValue);
        assertEquals(CREATE_STUDENT, actualResult);
    }

    @Test
    void fetchValue_shouldReturnNull_whenInputIsNegativeNumber() {
        int inputValue = -3;
        DBAction expectedResult = null;
        DBAction actualResult = DBAction.fetchValue(inputValue);
        assertEquals(expectedResult, actualResult);
    }

}
