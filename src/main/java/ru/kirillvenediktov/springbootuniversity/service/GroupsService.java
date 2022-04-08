package ru.kirillvenediktov.springbootuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kirillvenediktov.springbootuniversity.dao.GroupsDAO;
import ru.kirillvenediktov.springbootuniversity.dto.GroupDTO;
import ru.kirillvenediktov.springbootuniversity.dto.GroupWithStudentCount;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.models.Student;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupsService {

    private final GroupsDAO groupsDAO;
    private final DTOService dtoService;

    @Autowired
    public GroupsService(GroupsDAO groupsDAO, DTOService dtoService) {
        this.groupsDAO = groupsDAO;
        this.dtoService = dtoService;
    }

    @Transactional
    public List<GroupWithStudentCount> getGroupsWithStudentCount() {
        List<Group> groups = groupsDAO.getAllGroups();
        List<GroupWithStudentCount> groupWithStudentCounts = new ArrayList<>();
        for (Group group : groups) {
            groupWithStudentCounts.add(dtoService.getGroupWithStudentCountDTO(group));
        }
        return groupWithStudentCounts;
    }

    @Transactional
    public List<GroupDTO> getAllGroups() {
        List<Group> groups = groupsDAO.getAllGroups();
        List<GroupDTO> groupDTOS = new ArrayList<>();
        for (Group group : groups) {
            groupDTOS.add(dtoService.getGroupDTO(group));
        }
        return groupDTOS;
    }

    @Transactional
    public GroupDTO getGroup(Long groupId) {
        Group group = groupsDAO.getGroup(groupId);
        return dtoService.getGroupDTO(group);
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        Group group = groupsDAO.getGroup(groupId);
        groupsDAO.deleteGroup(group);
    }

    @Transactional
    public void saveGroup(GroupDTO groupDTO) {
        Group group = dtoService.getGroup(groupDTO);
        if (group.getId() == null) {
            groupsDAO.createGroup(group);
        } else {
            groupsDAO.updateGroup(group);
        }
    }

    @Transactional
    public List<StudentDTO> getStudentsOfGroup(Long groupId) {
        Group group = groupsDAO.getGroup(groupId);
        List<Student> students = group.getStudents();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(dtoService.getStudentDTO(student));
        }
        return studentDTOS;
    }

}
