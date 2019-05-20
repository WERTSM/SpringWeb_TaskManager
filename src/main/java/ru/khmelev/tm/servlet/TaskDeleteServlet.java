package ru.khmelev.tm.servlet;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.api.service.ITaskService;
import ru.khmelev.tm.service.TaskService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/taskDelete")
public class TaskDeleteServlet extends HttpServlet {

    @NotNull
    private final ITaskService taskService = TaskService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        @NotNull final String userId = (String) req.getSession().getAttribute("userId");
        taskService.removeTask(req.getParameter("TskId"), userId);
        resp.sendRedirect("/tasks");
    }
}