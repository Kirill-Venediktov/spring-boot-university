package ru.kirillvenediktov.springbootuniversity.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import ru.kirillvenediktov.springbootuniversity.models.Group;
import ru.kirillvenediktov.springbootuniversity.testConfig.TestDBConfig;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GroupsDAOTest {

    private static final String CREATION_SCRYPT_PATH = "databaseTesting/TestTableCreationScrypt.sql";
    private static final String FILLING_SCRYPT_PATH = "databaseTesting/testTableFillingScrypt.sql";
    private static final long EXISTED_GROUP_ID = 1L;

    private static ApplicationContext context;
    private static DataSource dataSource;
    private GroupsDAO groupsDAO;

    @BeforeAll
    public static void startInit() {
        context = new AnnotationConfigApplicationContext(TestDBConfig.class);
        dataSource = context.getBean("dataSource", DataSource.class);
    }

    @BeforeEach
    public void init() {
        groupsDAO = context.getBean("groupsDAO", GroupsDAO.class);
        runScrypt(CREATION_SCRYPT_PATH);
        runScrypt(FILLING_SCRYPT_PATH);
    }

    public void runScrypt(String path) {
        Resource resource = new ClassPathResource(path);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
    }

    @Test
    void getAllGroups_shouldReturnListOfGroupsDTO() {
        List<Group> expectedResult = new ArrayList<>();
        expectedResult.add(new Group(1L, "first group"));
        expectedResult.add(new Group(2L, "second group"));
        expectedResult.add(new Group(3L, "third group"));
        List<Group> actualResult = groupsDAO.getAllGroups();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void getGroup_shouldReturnGroupDTO_whenInputIsGroupId() {
        Group expectedResult = new Group(1L, "first group");
        Group actualResult = groupsDAO.getGroup(EXISTED_GROUP_ID);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void updateGroup_shouldReturnNumberOfChangedStrings_whenInputIsExistedGroup() {
        Group expectedResult = new Group(1L, "QWERTY group");
        Group actualResult = groupsDAO.updateGroup(new Group(1L, "QWERTY group"));
        assertEquals(expectedResult, actualResult);
    }

}
