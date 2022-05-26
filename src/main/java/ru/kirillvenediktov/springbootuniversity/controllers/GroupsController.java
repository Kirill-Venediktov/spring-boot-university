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
import ru.kirillvenediktov.springbootuniversity.dto.GroupWithStudentCount;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.service.GroupsService;
import ru.kirillvenediktov.springbootuniversity.service.PaginationService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class GroupsController {

    private static final String MODEL_OF_GROUP = "group";
    private static final String MODEL_OF_GROUP_PAGE = "groupPage";
    private static final String MODEL_OF_STUDENT_PAGE = "studentPage";
    private static final String MODEL_OF_PAGE_NUMBERS = "pageNumbers";
    private static final String REDIRECTED_GROUPS_PAGE = "redirect:/groups";
    private static final String EDIT_GROUP_PAGE = "editGroup";
    private static final int CURRENT_PAGE_NUMBER = 1;
    private static final int PAGE_SIZE = 10;

    private final GroupsService groupsService;
    private final PaginationService paginationService;

    @Autowired
    public GroupsController(GroupsService groupsService, PaginationService paginationService) {
        this.groupsService = groupsService;
        this.paginationService = paginationService;
    }

    @GetMapping("/groups")
    public String getGroups(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(CURRENT_PAGE_NUMBER);
        int pageSize = size.orElse(PAGE_SIZE);
        Page<GroupWithStudentCount> groupPage = groupsService.getGroupsPage(
            PageRequest.of(currentPage - 1, pageSize, Sort.by("id")));
        model.addAttribute(MODEL_OF_GROUP_PAGE, groupPage);
        int totalPages = groupPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute(MODEL_OF_PAGE_NUMBERS, pageNumbers);
        }
        return "groups";
    }

    @GetMapping("/groups/{id}")
    public String getGroupInfo(@PathVariable("id") Long id, Model model,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(CURRENT_PAGE_NUMBER);
        int pageSize = size.orElse(PAGE_SIZE);
        GroupDTO group = groupsService.getGroupDTO(id);
        model.addAttribute(MODEL_OF_GROUP, group);
        Page<StudentDTO> studentPage = paginationService.getPage(
            PageRequest.of(currentPage - 1, pageSize), groupsService.getStudentsOfGroup(id));
        model.addAttribute(MODEL_OF_STUDENT_PAGE, studentPage);
        model.addAttribute(MODEL_OF_PAGE_NUMBERS, paginationService.getPageNumbers(studentPage));
        return "groupInfo";
    }

    @GetMapping("/addGroup")
    public String getAddGroupForm(Model model) {
        GroupDTO group = new GroupDTO();
        model.addAttribute(MODEL_OF_GROUP, group);
        return EDIT_GROUP_PAGE;
    }

    @GetMapping("/editGroup/{id}")
    public String getEditGroupForm(@PathVariable("id") Long id, Model model) {
        GroupDTO group = groupsService.getGroupDTO(id);
        model.addAttribute(MODEL_OF_GROUP, group);
        return EDIT_GROUP_PAGE;
    }

    @PostMapping("/groups/{id}")
    public String delete(@PathVariable("id") Long id) {
        groupsService.deleteGroup(id);
        return REDIRECTED_GROUPS_PAGE;
    }

    @PostMapping("/saveGroup")
    public String saveGroup(@ModelAttribute(MODEL_OF_GROUP) @Valid GroupDTO group, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return EDIT_GROUP_PAGE;
        }
        groupsService.saveGroup(group);
        return REDIRECTED_GROUPS_PAGE;
    }

}
