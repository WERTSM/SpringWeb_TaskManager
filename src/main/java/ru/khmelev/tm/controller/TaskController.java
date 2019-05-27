package ru.khmelev.tm.controller;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.api.service.ITaskService;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.dto.TaskDTO;
import ru.khmelev.tm.enumeration.Status;
import ru.khmelev.tm.util.ConverterUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ManagedBean
@RequestScoped
public class TaskController extends SpringBeanAutowiringSupport  {

    @Autowired
    private ITaskService taskService;

    @Autowired
    private IProjectService projectService;

    @GetMapping("/taskCreate")
    public String taskCreatePage(@NotNull final HttpSession session, @NotNull final Model model) {
        @NotNull final String userId = (String) session.getAttribute("userId");
        @NotNull final Collection<ProjectDTO> projects = Objects.requireNonNull(projectService.findAll(userId));
        model.addAttribute("userId", userId);
        model.addAttribute("projects", projects);
        return "task/task-create";
    }

    @PostMapping("/taskCreate")
    public String taskCreate(
            @RequestParam("nameTask") final String nameTask,
            @RequestParam("descTask") final String descTask,
            @RequestParam("dateStart") final String dateStart,
            @RequestParam("dateFinish") final String dateFinish,
            @RequestParam("taskProjectId") final String taskProjectId,
            final HttpSession session
    ) {
        @NotNull final TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(UUID.randomUUID().toString());
        taskDTO.setName(nameTask);
        taskDTO.setDescription(descTask);
        taskDTO.setDateCreate(new Date());
        taskDTO.setDateStart(ConverterUtil.convertFromStringToDate(dateStart));
        taskDTO.setDateFinish(ConverterUtil.convertFromStringToDate(dateFinish));
        taskDTO.setStatus(Status.INPROGRESS);
        taskDTO.setProjectId(taskProjectId);
        taskDTO.setUserId((String) session.getAttribute("userId"));
        taskService.createTask(taskDTO.getId(), taskDTO);
        return "redirect:/tasks";
    }

    @GetMapping("/taskEdit")
    public String taskEditPage(
            final HttpSession session,
            @RequestParam("TskId") final String taskId,
            @NotNull final Model model
    ) {
        @NotNull final String userId = (String) session.getAttribute("userId");

        @Nullable final TaskDTO taskDTO = taskService.findTask(taskId, userId);
        model.addAttribute("task", taskDTO);

        @NotNull final Collection<ProjectDTO> projects = Objects.requireNonNull(projectService.findAll(userId));
        model.addAttribute("projects", projects);
        return "task/task-edit";
    }

    @PostMapping("/taskEdit")
    public String taskEdit(
            @RequestParam("idTask") final String idTask,
            @RequestParam("nameTask") final String nameTask,
            @RequestParam("descTask") final String descTask,
            @RequestParam("dateStart") final String dateStart,
            @RequestParam("dateFinish") final String dateFinish,
            @RequestParam("taskProjectId") final String taskProjectId,
            final HttpSession session
    ) {
        @NotNull final TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(idTask);
        taskDTO.setName(nameTask);
        taskDTO.setDescription(descTask);
        taskDTO.setDateCreate(new Date());
        taskDTO.setDateStart(ConverterUtil.convertFromStringToDate(dateStart));
        taskDTO.setDateFinish(ConverterUtil.convertFromStringToDate(dateFinish));
        taskDTO.setStatus(Status.INPROGRESS);
        taskDTO.setProjectId(taskProjectId);
        @NotNull final String userId = (String) session.getAttribute("userId");
        taskDTO.setUserId(userId);
        taskService.editTask(taskDTO.getId(), taskDTO, userId);
        return "redirect:/tasks";
    }

    @GetMapping("/tasks")
    public String tasksPage(final HttpSession session, final Model model) {
        @NotNull final String userId = (String) session.getAttribute("userId");
        @NotNull final Collection<TaskDTO> tasks = Objects.requireNonNull(taskService.findAll(userId));
        model.addAttribute("tasks", tasks);
        return "task/task-list";
    }

    @PostMapping("/taskDelete")
    public String taskDelete(final HttpSession session, @RequestParam("TskId") final String taskId) {
        @NotNull final String userId = (String) session.getAttribute("userId");
        taskService.removeTask(taskId, userId);
        return "redirect:/tasks";
    }
}