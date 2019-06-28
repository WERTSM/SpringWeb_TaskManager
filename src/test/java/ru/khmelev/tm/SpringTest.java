package ru.khmelev.tm;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.enumeration.Role;
import ru.khmelev.tm.enumeration.Status;
import ru.khmelev.tm.util.ConverterUtil;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@DataJpaTest
public class SpringTest {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserDTO testUserDTO;

    @Before
    public void createTestUser() {
        @NotNull final UserDTO userDTO = new UserDTO();
        userDTO.setId(UUID.randomUUID().toString());
        @NotNull final String hashPassword = Objects.requireNonNull(bCryptPasswordEncoder.encode("test"));
        userDTO.setHashPassword(hashPassword);
        userDTO.setLogin("test");
        userDTO.setRole(Role.USER);
        userService.createUser(userDTO.getId(), userDTO);
        testUserDTO = userDTO;
    }

    @Test
    public void createAndFindEntity() {
        @NotNull final ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(UUID.randomUUID().toString());
        projectDTO.setName("ProjectNameTest");
        projectDTO.setDescription("ProjectDescriptionTest");
        projectDTO.setDateCreate(new Date());
        projectDTO.setDateStart(new Date());
        projectDTO.setDateFinish(new Date());
        projectDTO.setStatus(Status.INPROGRESS);
        projectDTO.setUserId(testUserDTO.getId());

        projectService.createProject(projectDTO.getId(), projectDTO);

        @NotNull final ProjectDTO projectDTOfromServer = Objects.requireNonNull(projectService.findProject(projectDTO.getId(), testUserDTO.getId()));
        Assert.assertEquals(projectDTOfromServer.getId(), projectDTO.getId());
        Assert.assertEquals(projectDTOfromServer.getName(), projectDTO.getName());
        Assert.assertEquals(projectDTOfromServer.getDescription(), projectDTO.getDescription());
        Assert.assertEquals(ConverterUtil.convertDateFormat(projectDTOfromServer.getDateStart()), ConverterUtil.convertDateFormat(projectDTO.getDateStart()));
        Assert.assertEquals(ConverterUtil.convertDateFormat(projectDTOfromServer.getDateFinish()), ConverterUtil.convertDateFormat(projectDTO.getDateFinish()));
        Assert.assertEquals(ConverterUtil.convertDateFormat(projectDTOfromServer.getDateCreate()), ConverterUtil.convertDateFormat(projectDTO.getDateCreate()));
        Assert.assertEquals(projectDTOfromServer.getStatus(), projectDTO.getStatus());
        Assert.assertEquals(projectDTOfromServer.getUserId(), projectDTO.getUserId());
    }

    @After
    public void deleteTestEntity() {
        userService.removeUser(testUserDTO.getId());
    }
}