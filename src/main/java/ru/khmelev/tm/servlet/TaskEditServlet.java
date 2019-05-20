package ru.khmelev.tm.servlet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.api.service.ITaskService;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.dto.TaskDTO;
import ru.khmelev.tm.enumeration.Status;
import ru.khmelev.tm.service.ProjectService;
import ru.khmelev.tm.service.TaskService;
import ru.khmelev.tm.util.ConverterUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

@WebServlet("/taskEdit")
public class TaskEditServlet extends HttpServlet {

    @NotNull
    private final ITaskService taskService = TaskService.getInstance();

    @NotNull
    private final IProjectService projectService = ProjectService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @NotNull final String userId = (String) req.getSession().getAttribute("userId");

        @Nullable final TaskDTO taskDTO = taskService.findTask(req.getParameter("TskId"), userId);
        req.setAttribute("task", taskDTO);

        @NotNull final Collection<ProjectDTO> projects = projectService.findAll(userId);
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("WEB-INF/views/task/taskEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        @NotNull final TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(req.getParameter("idTask"));
        taskDTO.setName(req.getParameter("nameTask"));
        taskDTO.setDescription(req.getParameter("descTask"));
        taskDTO.setDateCreate(new Date());
        taskDTO.setDateStart(ConverterUtil.convertFromStringToDate(req.getParameter("dateStart")));
        taskDTO.setDateFinish(ConverterUtil.convertFromStringToDate(req.getParameter("dateFinish")));
        taskDTO.setStatus(Status.INPROGRESS);
        taskDTO.setProjectId(req.getParameter("taskProjectId"));
        taskDTO.setUserId((String) req.getSession().getAttribute("userId"));
        taskService.createTask(taskDTO.getId(), taskDTO);
        resp.sendRedirect(req.getContextPath() + "/user");
    }
}