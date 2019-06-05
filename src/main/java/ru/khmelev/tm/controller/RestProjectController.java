package ru.khmelev.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.dto.ProjectDTO;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@RestController
public class RestProjectController {

    @Autowired
    private IProjectService projectService;

    @PostMapping(value = "/projectCreate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean projectCreate(@RequestBody @NotNull final ProjectDTO projectDTO) {
        projectService.createProject(projectDTO.getId(), projectDTO);
        return true;
    }

    @Nullable
    @GetMapping(value = "/findProject/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectDTO findProject(final HttpSession session, @PathVariable(value = "id") @NotNull final String id) {
        return projectService.findProject(id, (String) session.getAttribute("userId"));
    }

    @NotNull
    @GetMapping(value = "/findAllProject", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<ProjectDTO> findAllProject(final HttpSession session) {
        return projectService.findAll((String) session.getAttribute("userId"));
    }

    @PutMapping(value = "/projectEdit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean projectEdit(final HttpSession session, @RequestBody @NotNull final ProjectDTO projectDTO) {
        projectService.editProject(projectDTO.getId(), projectDTO, (String) session.getAttribute("userId"));
        return true;
    }

    @DeleteMapping(value = "/projectDelete/{id}")
    public Boolean projectDelete(final HttpSession session, @PathVariable(value = "id") @NotNull final String id) {
        projectService.removeProject(id, (String) session.getAttribute("userId"));
        return true;
    }
}