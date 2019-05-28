package ru.khmelev.tm.controller;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.api.service.ITaskService;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.dto.TaskDTO;
import ru.khmelev.tm.enumeration.Status;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ManagedBean
@ViewScoped
@URLMappings(
        mappings = {
                @URLMapping(id = "taskList", pattern = "/taskList", viewId = "/WEB-INF/views/task/task-list.xhtml"),
                @URLMapping(id = "taskCreate", pattern = "/taskCreate", viewId = "/WEB-INF/views/task/task-create.xhtml"),
                @URLMapping(id = "taskEdit", pattern = "/taskEdit", viewId = "/WEB-INF/views/task/task-edit.xhtml")
        })
public class TaskController extends SpringBeanAutowiringSupport {


    @Autowired
    private ITaskService taskService;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IUserService userService;

    @NotNull
    private TaskDTO taskDTO = new TaskDTO();

    @NotNull
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    @NotNull
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    @NotNull

    private
    String userId = (String) session.getAttribute("userId");

    private
    String id;

    public List<TaskDTO> taskList() {
        return (List<TaskDTO>) taskService.findAll(userId);
    }

    public List<ProjectDTO> projectList() {
        return (List<ProjectDTO>) projectService.findAll(userId);
    }

    public TaskDTO findTask() {
        @Nullable final TaskDTO taskDTO = taskService.findTask(id, userId);
        if (taskDTO != null) {
            this.taskDTO = taskDTO;
        }
        return this.taskDTO;
    }

    public String taskCreate() {
        taskDTO.setId(UUID.randomUUID().toString());
        taskDTO.setDateCreate(new Date());
        taskDTO.setStatus(Status.INPROGRESS);
        taskDTO.setUserId(userId);
        taskService.createTask(taskDTO.getId(), taskDTO);
        return "task-list?faces-redirect=true";
    }

    public String taskEdit() {
        taskService.editTask(id, taskDTO, userId);
        return "task-list?faces-redirect=true";
    }

    public String taskDelete(String id) {
        taskService.removeTask(id, userId);
        return "project-list";
    }
}