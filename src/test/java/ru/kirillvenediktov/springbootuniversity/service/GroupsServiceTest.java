package ru.kirillvenediktov.springbootuniversity.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kirillvenediktov.springbootuniversity.dao.GroupsDAO;
import ru.kirillvenediktov.springbootuniversity.dto.GroupDTO;
import ru.kirillvenediktov.springbootuniversity.models.Group;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class GroupsServiceTest {

    private GroupsService groupsService;
    @MockBean
    private GroupsDAO groupsDAO;
    @MockBean
    private DTOService dtoService;

    @BeforeEach
    public void init() {
        groupsService = new GroupsService(groupsDAO, dtoService);
    }

    @Test
    void getGroupsWithStudentCount_shouldReturnListOfGroupsWithStudentsCount() {
        assertNotNull(groupsService.getGroupsWithStudentCount());
    }

    @Test
    void getAllGroups_shouldReturnGroupsDTOList() {
        assertNotNull(groupsService.getAllGroups());
    }

    @Test
    void getGroup_shouldReturnGroupDTO_whenInputIsGroupId() {
        Mockito.when(groupsDAO.getGroup(Mockito.anyLong())).thenReturn(new Group());
        Mockito.when(dtoService.getGroupDTO(Mockito.any())).thenReturn(new GroupDTO());
        assertNotNull(groupsService.getGroup(Mockito.anyLong()));
    }

}
