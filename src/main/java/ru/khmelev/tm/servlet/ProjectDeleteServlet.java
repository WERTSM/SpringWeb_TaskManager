package ru.khmelev.tm.servlet;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.service.ProjectService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/projectDelete")
public class ProjectDeleteServlet extends HttpServlet {

    @NotNull
    private final IProjectService projectService = ProjectService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        @NotNull final String userId = (String) req.getSession().getAttribute("userId");
        projectService.removeProject(req.getParameter("PrId"), userId);
        resp.sendRedirect("/projects");
    }
}