package ru.khmelev.tm.controller;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.enumeration.Status;
import ru.khmelev.tm.util.ViewScope;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Component
@Scope(ViewScope.NAME)
@URLMappings(
        mappings = {
                @URLMapping(id = "projectList", pattern = "/projectList", viewId = "/WEB-INF/views/project/project-list.xhtml"),
                @URLMapping(id = "projectCreate", pattern = "/projectCreate", viewId = "/WEB-INF/views/project/project-create.xhtml"),
                @URLMapping(id = "projectEdit", pattern = "/projectEdit", viewId = "/WEB-INF/views/project/project-edit.xhtml")
        })
public class ProjectController extends SpringBeanAutowiringSupport {

    private String id;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IUserService userService;

    @NotNull
    private ProjectDTO projectDTO = new ProjectDTO();

    @NotNull
    private FacesContext facesContext = FacesContext.getCurrentInstance();

    @NotNull
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

    @NotNull

    private String userId = (String) session.getAttribute("userId");

    public List<ProjectDTO> projectList() {
        return (List<ProjectDTO>) projectService.findAll(userId);
    }

    public ProjectDTO findProject() {
        @Nullable final ProjectDTO projectDTO = projectService.findProject(id, userId);
        if (projectDTO != null) {
            this.projectDTO = projectDTO;
        }
        return this.projectDTO;
    }

    public String projectCreate() {
        System.out.println(projectDTO.getName());
        projectDTO.setId(UUID.randomUUID().toString());
        projectDTO.setDateCreate(new Date());
        projectDTO.setStatus(Status.INPROGRESS);
        projectDTO.setUserId(userId);
        projectService.createProject(projectDTO.getId(), projectDTO);
        return "project-list?faces-redirect=true";
    }

    public String projectEdit() {
        projectService.editProject(id, projectDTO, userId);
        return "project-list?faces-redirect=true";
    }

    public String projectDelete(String id) {
        projectService.removeProject(id, userId);
        return "project-list";
    }
}