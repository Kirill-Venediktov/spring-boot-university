package ru.kirillvenediktov.springbootuniversity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginationServiceTest {

    private final int CURRENT_PAGE = 0;
    private final int PAGE_SIZE = 10;
    private PaginationService paginationService;

    @BeforeEach
    public void init() {
        paginationService = new PaginationService();
    }

    @Test
    void findPaginatedStudent_shouldReturnStudentsPage_whenInputIsPageableAndList() {
        List<StudentDTO> students = new ArrayList<>();
        students.add(new StudentDTO(1L, 2L, "Ivan", "Petrov"));
        students.add(new StudentDTO(2L, 3L, "Petr", "Ivanov"));
        Page<StudentDTO> expectedResult = new PageImpl<>(students, PageRequest.of(CURRENT_PAGE, PAGE_SIZE), students.size());
        Page<StudentDTO> actualResult = paginationService.getPage(PageRequest.of(CURRENT_PAGE, PAGE_SIZE), students);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getPageNumbers_shouldReturnListWithIntegers_whenInputIsPage() {
        List<StudentDTO> students = new ArrayList<>();
        students.add(new StudentDTO(1L, 2L, "Ivan", "Petrov"));
        students.add(new StudentDTO(2L, 3L, "Petr", "Ivanov"));
        Page<StudentDTO> page = new PageImpl<>(students, PageRequest.of(CURRENT_PAGE, PAGE_SIZE), students.size());
        int totalPages = page.getTotalPages();
        List<Integer> expectedResult = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
        List<Integer> actualResult = paginationService.getPageNumbers(page);
        assertEquals(expectedResult, actualResult);
    }

}
