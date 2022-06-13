package ru.kirillvenediktov.springbootuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kirillvenediktov.springbootuniversity.dto.GroupDTO;
import ru.kirillvenediktov.springbootuniversity.dto.GroupWithStudentCount;
import ru.kirillvenediktov.springbootuniversity.dto.StudentDTO;
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.models.Student;
import ru.kirillvenediktov.springbootuniversity.repositories.GroupRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupsService {

    private final GroupRepository groupRepository;
    private final DTOService dtoService;

    @Autowired
    public GroupsService(GroupRepository groupRepository, DTOService dtoService) {
        this.groupRepository = groupRepository;
        this.dtoService = dtoService;
    }

    @Transactional
    public Page<GroupWithStudentCount> getGroupsPage(Pageable pageable) {
        Page<Group> groups = groupRepository.findAll(pageable);
        List<GroupWithStudentCount> groupDTOS = new ArrayList<>();
        for (Group group : groups) {
            groupDTOS.add(dtoService.getGroupWithStudentCountDTO(group));
        }
        return new PageImpl<>(groupDTOS, pageable, groupRepository.findAll().size());
    }

    @Transactional
    public List<GroupDTO> getAllGroupsDTO() {
        List<Group> groups = groupRepository.findAll();
        List<GroupDTO> groupDTOS = new ArrayList<>();
        for (Group group : groups) {
            groupDTOS.add(dtoService.getGroupDTO(group));
        }
        return groupDTOS;
    }

    @Transactional
    public Group getGroup(Long groupId) {
        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        Group group = new Group();
        if (optionalGroup.isPresent()) {
            group = optionalGroup.get();
        }
        return group;
    }

    @Transactional
    public GroupDTO getGroupDTO(Long groupId) {
        return dtoService.getGroupDTO(getGroup(groupId));
    }

    @Transactional
    public void deleteGroup(Long groupId) {
        Group group = getGroup(groupId);
        groupRepository.delete(group);
    }

    @Transactional
    public void saveGroup(GroupDTO groupDTO) {
        Group group;
        if (groupDTO.getId() == null) {
            group = dtoService.getGroup(groupDTO);
            groupRepository.save(group);
        } else {
            group = getGroup(groupDTO.getId());
            group.setGroupName(groupDTO.getGroupName());
        }
    }

    @Transactional
    public List<StudentDTO> getStudentsOfGroup(Long groupId) {
        Group group = getGroup(groupId);
        List<Student> students = group.getStudents();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : students) {
            studentDTOS.add(dtoService.getStudentDTO(student));
        }
        return studentDTOS;
    }

}
