package ru.khmelev.tm.servlet;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.api.service.ITaskService;
import ru.khmelev.tm.dto.TaskDTO;
import ru.khmelev.tm.service.TaskService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/tasks")
public class TaskListServlet extends HttpServlet {

    @NotNull
    private final ITaskService taskService = TaskService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @NotNull final String userId = (String) req.getSession().getAttribute("userId");
        @NotNull final Collection<TaskDTO> tasks = taskService.findAll(userId);
        req.setAttribute("tasks", tasks);
        @NotNull final RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/task/taskList.jsp");
        requestDispatcher.forward(req, resp);
    }
}