package ru.kirillvenediktov.springbootuniversity.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kirillvenediktov.springbootuniversity.dto.CourseDTO;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.models.Course;
import ru.kirillvenediktov.springbootuniversity.service.CoursesService;
import ru.kirillvenediktov.springbootuniversity.service.PaginationService;
import ru.kirillvenediktov.springbootuniversity.service.StudentsService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CoursesController {

    private static final String MODEL_OF_STUDENT_ID = "studentId";
    private static final String MODEL_OF_COURSE = "course";
    private static final String MODEL_OF_COURSES_LIST = "coursesList";
    private static final String MODEL_OF_COURSE_PAGE = "coursePage";
    private static final String MODEL_OF_STUDENT_PAGE = "studentPage";
    private static final String MODEL_OF_PAGE_NUMBERS = "pageNumbers";
    private static final String REDIRECTED_COURSES_PAGE = "redirect:/courses";
    private static final String EDIT_COURSE_PAGE = "editCourse";
    private static final int CURRENT_PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 10;

    private final CoursesService coursesService;
    private final StudentsService studentsService;
    private final PaginationService paginationService;

    @Autowired
    public CoursesController(CoursesService coursesService, StudentsService studentsService,
                             PaginationService paginationService) {
        this.coursesService = coursesService;
        this.studentsService = studentsService;
        this.paginationService = paginationService;
    }

    @GetMapping("/courses")
    public String getCourses(Model model,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(CURRENT_PAGE_NUMBER);
        int pageSize = size.orElse(PAGE_SIZE);
        Page<CourseDTO> coursePage = coursesService.getCoursesPage(
            PageRequest.of(currentPage - 1, pageSize, Sort.by("id")));
        model.addAttribute(MODEL_OF_COURSE_PAGE, coursePage);
        model.addAttribute(MODEL_OF_PAGE_NUMBERS, paginationService.getPageNumbers(coursePage));
        return "courses";
    }

    @GetMapping("/courses/{id}")
    public String getCourseInfo(@PathVariable("id") Long id, Model model,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(CURRENT_PAGE_NUMBER);
        int pageSize = size.orElse(PAGE_SIZE);
        CourseDTO course = coursesService.getCourseDTO(id);
        model.addAttribute(MODEL_OF_COURSE, course);
        Page<StudentDTO> studentPage = paginationService.getPage(
            PageRequest.of(currentPage - 1, pageSize), coursesService.getStudentsOfCourse(id));
        model.addAttribute(MODEL_OF_STUDENT_PAGE, studentPage);
        int totalPages = studentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(MODEL_OF_PAGE_NUMBERS, pageNumbers);
        }
        return "courseInfo";
    }

    @GetMapping("/addToCourse/{id}")
    public String getAddToCourseForm(@PathVariable("id") Long id, Model model) {
        Course course = new Course();
        List<CourseDTO> courseList = coursesService.getAllCoursesDTO();
        courseList.removeAll(studentsService.getStudentCourses(id));
        model.addAttribute(MODEL_OF_STUDENT_ID, id);
        model.addAttribute(MODEL_OF_COURSE, course);
        model.addAttribute(MODEL_OF_COURSES_LIST, courseList);
        return "addToCourse";
    }

    @GetMapping("/deleteFromCourse/{id}")
    public String getDeleteFromCourseForm(@PathVariable("id") Long id, Model model) {
        CourseDTO course = new CourseDTO();
        List<CourseDTO> courseList = studentsService.getStudentCourses(id);
        model.addAttribute(MODEL_OF_STUDENT_ID, id);
        model.addAttribute(MODEL_OF_COURSE, course);
        model.addAttribute(MODEL_OF_COURSES_LIST, courseList);
        return "deleteFromCourse";
    }

    @GetMapping("/addCourse")
    public String getAddCourseForm(Model model) {
        CourseDTO course = new CourseDTO();
        model.addAttribute(MODEL_OF_COURSE, course);
        return EDIT_COURSE_PAGE;
    }

    @GetMapping("/editCourse/{id}")
    public String getEditCourseForm(@PathVariable("id") Long id, Model model) {
        CourseDTO course = coursesService.getCourseDTO(id);
        model.addAttribute(MODEL_OF_COURSE, course);
        return EDIT_COURSE_PAGE;
    }

    @PostMapping("/saveCourse")
    public String saveCourse(@ModelAttribute(MODEL_OF_COURSE) @Valid CourseDTO course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return EDIT_COURSE_PAGE;
        }
        coursesService.saveCourse(course);
        return REDIRECTED_COURSES_PAGE;
    }

    @PostMapping("/addCourse/{id}")
    public String addToCourse(@PathVariable("id") Long id, @ModelAttribute("course") CourseDTO course) {
        StudentDTO studentDTO = studentsService.getStudentDTO(id);
        coursesService.addStudentToCourse(studentDTO, course.getId());
        return "redirect:/students/{id}";
    }

    @PostMapping("/deleteStudentFromCourse/{id}")
    public String deleteFromCourse(@PathVariable("id") Long id, @ModelAttribute(MODEL_OF_COURSE) CourseDTO course) {
        StudentDTO studentDTO = studentsService.getStudentDTO(id);
        coursesService.removeStudentFromCourse(studentDTO, course.getId());
        return "redirect:/students/{id}";
    }

    @PostMapping("/courses/{id}")
    public String delete(@PathVariable("id") Long id) {
        coursesService.deleteCourse(id);
        return REDIRECTED_COURSES_PAGE;
    }

}
