package ru.khmelev.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.enumeration.Status;
import ru.khmelev.tm.util.ConverterUtil;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Controller
public class ProjecctController {

    @Autowired
    private
    IUserService userService;

    @Autowired
    private
    IProjectService projectService;

    @GetMapping("/projectCreate")
    public String projectCreatePage() {
        return ("project/project-create");
    }

    @PostMapping("/projectCreate")
    public String projectCreate(
            @RequestParam("nameProject") final String nameProject,
            @RequestParam("descProject") final String descProject,
            @RequestParam("dateStart") final String dateStart,
            @RequestParam("dateFinish") final String dateFinish,
            final HttpSession session
    ) {
        @NotNull final ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(UUID.randomUUID().toString());
        projectDTO.setName(nameProject);
        projectDTO.setDescription(descProject);
        projectDTO.setDateCreate(new Date());
        projectDTO.setDateStart(ConverterUtil.convertFromStringToDate(dateStart));
        projectDTO.setDateFinish(ConverterUtil.convertFromStringToDate(dateFinish));
        projectDTO.setStatus(Status.INPROGRESS);
        projectDTO.setUserId((String) session.getAttribute("userId"));
        projectService.createProject(projectDTO.getId(), projectDTO);
        return "redirect:/projects";
    }

    @GetMapping("/projectEdit")
    public String projectEditPage(final HttpSession session, @RequestParam("PrId") final String projectId, final Model model) {
        @Nullable final ProjectDTO projectDTO = projectService.findProject(projectId, (String) session.getAttribute("userId"));
        model.addAttribute("project", projectDTO);
        return "project/project-edit";
    }

    @PostMapping("/projectEdit")
    public String projectEdit(
            @RequestParam("idProject") final String idProject,
            @RequestParam("nameProject") final String nameProject,
            @RequestParam("descProject") final String descProject,
            @RequestParam("dateStart") final String dateStart,
            @RequestParam("dateFinish") final String dateFinish,
            final HttpSession session
    ) {
        @NotNull final ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(idProject);
        projectDTO.setName(nameProject);
        projectDTO.setDescription(descProject);
        projectDTO.setDateCreate(new Date());
        projectDTO.setDateStart(ConverterUtil.convertFromStringToDate(dateStart));
        projectDTO.setDateFinish(ConverterUtil.convertFromStringToDate(dateFinish));
        projectDTO.setStatus(Status.INPROGRESS);
        @NotNull final String userId = (String) session.getAttribute("userId");
        projectDTO.setUserId(userId);
        projectService.editProject(projectDTO.getId(), projectDTO, userId);
        return "redirect:/projects";
    }

    @GetMapping("/projects")
    public String projectsPage(final HttpSession session, final Model model) {
        @NotNull final String userId = (String) session.getAttribute("userId");
        @NotNull final Collection<ProjectDTO> projects = Objects.requireNonNull(projectService.findAll(userId));
        model.addAttribute("projects", projects);
        return "project/project-list";
    }

    @PostMapping("/projectDelete")
    public String projectDelete(final HttpSession session, @RequestParam("PrId") String projectId) {
        @NotNull final String userId = (String) session.getAttribute("userId");
        projectService.removeProject(projectId, userId);
        return "redirect:/projects";
    }
}