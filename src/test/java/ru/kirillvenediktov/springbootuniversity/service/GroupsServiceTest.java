package ru.kirillvenediktov.springbootuniversity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kirillvenediktov.springbootuniversity.repositories.GroupRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GroupsServiceTest {

    private GroupsService groupsService;

    @MockBean
    private GroupRepository groupRepository;

    @MockBean
    private DTOService dtoService;

    @BeforeEach
    public void init() {
        groupsService = new GroupsService(groupRepository, dtoService);
    }

    @Test
    void getAllGroups_shouldReturnGroupsDTOList() {
        assertNotNull(groupsService.getAllGroupsDTO());
    }

    @Test
    void getGroup_shouldReturnGroup_whenInputIsGroupId() {
        assertNotNull(groupsService.getGroup(Mockito.anyLong()));
    }

    @Test
    void getStudentsOfGroup_shouldReturnListOfStudentsDTO_whenInputIsGroupId() {
        assertNotNull(groupsService.getStudentsOfGroup(Mockito.anyLong()));
    }

}
