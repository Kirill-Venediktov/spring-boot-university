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
import ru.kirillvenediktov.springbootuniversity.dto.GroupDTO;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.service.GroupsService;
import ru.kirillvenediktov.springbootuniversity.service.StudentsService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class StudentsController {

    private static final String MODEL_OF_STUDENT = "student";
    private static final String MODEL_OF_GROUP = "group";
    private static final String MODEL_OF_STUDENT_PAGE = "studentPage";
    private static final String MODEL_OF_PAGE_NUMBERS = "pageNumbers";
    private static final String MODEL_OF_GROUPS_LIST = "groupsList";
    private static final String MODEL_OF_COURSES_LIST = "coursesList";
    private static final String REDIRECTED_STUDENTS_PAGE = "redirect:/students";
    private static final String EDIT_STUDENT_PAGE = "editStudent";
    private static final int CURRENT_PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 10;

    private final StudentsService studentsService;
    private final GroupsService groupsService;

    @Autowired
    public StudentsController(StudentsService studentsService, GroupsService groupsService) {
        this.studentsService = studentsService;
        this.groupsService = groupsService;

    }

    @GetMapping("/students")
    public String getStudents(Model model,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(CURRENT_PAGE_NUMBER);
        int pageSize = size.orElse(PAGE_SIZE);
        Page<StudentDTO> studentPage = studentsService.getStudentPage(
            PageRequest.of(currentPage - 1, pageSize, Sort.by("id")));
        model.addAttribute(MODEL_OF_STUDENT_PAGE, studentPage);
        int totalPages = studentPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(MODEL_OF_PAGE_NUMBERS, pageNumbers);
        }
        return "students";
    }

    @GetMapping("/addStudent")
    public String getAddStudentForm(Model model) {
        StudentDTO student = new StudentDTO();
        GroupDTO group = new GroupDTO();
        model.addAttribute(MODEL_OF_STUDENT, student);
        model.addAttribute(MODEL_OF_GROUP, group);
        model.addAttribute(MODEL_OF_GROUPS_LIST, groupsService.getAllGroupsDTO());
        return EDIT_STUDENT_PAGE;
    }

    @GetMapping("/editStudent/{id}")
    public String getEditStudentForm(@PathVariable("id") Long id, Model model) {
        StudentDTO student = studentsService.getStudentDTO(id);
        GroupDTO group = groupsService.getGroupDTO(student.getGroupId());
        model.addAttribute(MODEL_OF_STUDENT, student);
        model.addAttribute(MODEL_OF_GROUP, group);
        model.addAttribute(MODEL_OF_GROUPS_LIST, groupsService.getAllGroupsDTO());
        return EDIT_STUDENT_PAGE;
    }

    @GetMapping("/students/{id}")
    public String getStudentInfo(@PathVariable("id") Long id, Model model) {
        StudentDTO student = studentsService.getStudentDTO(id);
        GroupDTO group = new GroupDTO();
        if (student.getGroupId() != null) {
            group = groupsService.getGroupDTO(student.getGroupId());
        }
        model.addAttribute(MODEL_OF_STUDENT, student);
        model.addAttribute(MODEL_OF_GROUP, group);
        model.addAttribute(MODEL_OF_COURSES_LIST, studentsService.getStudentCourses(id));
        return "studentInfo";
    }

    @PostMapping("/students/{id}")
    public String delete(@PathVariable("id") Long id) {
        studentsService.deleteStudent(id);
        return REDIRECTED_STUDENTS_PAGE;
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute(MODEL_OF_STUDENT) @Valid StudentDTO studentDTO,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(MODEL_OF_STUDENT, studentDTO);
            model.addAttribute(MODEL_OF_GROUPS_LIST, groupsService.getAllGroupsDTO());
            return EDIT_STUDENT_PAGE;
        }
        studentsService.saveStudent(studentDTO);
        return REDIRECTED_STUDENTS_PAGE;
    }

}
