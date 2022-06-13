package ru.kirillvenediktov.springbootuniversity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.kirillvenediktov.springbootuniversity.dto.GroupDTO;
import ru.kirillvenediktov.springbootuniversity.exceptions.ChangesNotMadeException;
import ru.kirillvenediktov.springbootuniversity.service.GroupsService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestGroupsController {

    private final GroupsService groupsService;

    @Autowired
    public RestGroupsController(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @GetMapping("/groups")
    public List<GroupDTO> getGroups() {
        return groupsService.getAllGroupsDTO();
    }

    @GetMapping("/groups/{id}")
    public GroupDTO getGroup(@PathVariable Long id) {
        return groupsService.getGroupDTO(id);
    }

    @RequestMapping(value = "/groups", method = {RequestMethod.POST, RequestMethod.PUT})
    public GroupDTO saveGroup(@RequestBody GroupDTO groupDTO) {
        groupsService.saveGroup(groupDTO);
        return groupDTO;
    }

    @DeleteMapping("/groups/{id}")
    public String deleteGroup(@PathVariable Long id) {
        if (groupsService.getGroupDTO(id).getId() == null) {
            throw new ChangesNotMadeException("there is no group with id = " + id + " in database");
        }
        groupsService.deleteGroup(id);
        return "Group with id = " + id + " was deleted";
    }

}
