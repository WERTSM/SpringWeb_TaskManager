package ru.khmelev.tm.controller;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.enumeration.Status;
import ru.khmelev.tm.util.ConverterUtil;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ManagedBean
@RequestScoped
@URLMappings(
        mappings = {
                @URLMapping(id = "projectList", pattern = "/projectList", viewId = "/WEB-INF/views/project/project-list.xhtml")
        })
public class ProjecctController extends SpringBeanAutowiringSupport  {

    @Autowired
    private
    IProjectService projectService;

    @Autowired
    private IUserService userService;

    ProjectDTO project;

    @NotNull
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    @NotNull
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    @NotNull
    private
    String userId = (String) session.getAttribute("userId");

    public List<ProjectDTO> projectList() {
        return (List<ProjectDTO>) projectService.findAll(userId);
    }

    public String projectCreate() {
        @NotNull final ProjectDTO projectDTO = new ProjectDTO();
        project.setId(UUID.randomUUID().toString());

        project.setDateCreate(new Date());

        project.setStatus(Status.INPROGRESS);
        project.setUserId(userId);
        projectService.createProject(projectDTO.getId(), projectDTO);
        return "project/project-list.xhtml?faces-redirect=true";
    }

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
        return "project/project-list.xhtml?faces-redirect=true";
    }

    public String projectDelete(final HttpSession session, @RequestParam("PrId") String projectId) {
        @NotNull final String userId = (String) session.getAttribute("userId");
        projectService.removeProject(projectId, userId);
        return "project/project-list.xhtml?faces-redirect=true";
    }
}