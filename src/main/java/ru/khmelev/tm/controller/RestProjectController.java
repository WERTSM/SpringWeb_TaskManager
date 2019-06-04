package ru.khmelev.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.dto.ProjectDTO;

import javax.jws.WebParam;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;

@RestController
public class RestProjectController {

    @Autowired
    private IProjectService projectService;

    @PostMapping(value = "/projectCreate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean projectCreate(@RequestBody final ProjectDTO projectDTO) {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(projectDTO.getDateStart());
        projectService.createProject(projectDTO.getId(), projectDTO);
        return true;
    }

    @GetMapping(value = "/findProject/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDTO findProject(final HttpSession session, @PathVariable(value = "id") @NotNull final String id) {
        ProjectDTO projectDTO = projectService.findProject(id, (String) session.getAttribute("userId"));
        System.out.println(projectDTO.getDateStart());
        return projectDTO;
    }

    @GetMapping(value = "/findAllProject", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ProjectDTO> findAllProject(final HttpSession session) {
        @Nullable final Collection<ProjectDTO> collection = projectService.findAll((String) session.getAttribute("userId"));
        return projectService.findAll((String) session.getAttribute("userId"));
    }

    @PutMapping(value = "/projectEdit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void projectEdit(final HttpSession session, @RequestBody ProjectDTO projectDTO) {
        projectService.editProject(projectDTO.getId(), projectDTO, (String) session.getAttribute("userId"));
    }

    @DeleteMapping(value = "/projectDelete/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void projectDelete(final HttpSession session, @PathVariable(value = "id") String id) {
        projectService.removeProject(id, (String) session.getAttribute("userId"));
    }
}