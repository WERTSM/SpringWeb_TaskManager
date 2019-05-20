package ru.khmelev.tm.servlet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.enumeration.Status;
import ru.khmelev.tm.service.ProjectService;
import ru.khmelev.tm.util.ConverterUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/projectEdit")
public class ProjectEditServlet extends HttpServlet {

    @NotNull
    private final IProjectService projectService = ProjectService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        @Nullable final ProjectDTO projectDTO = projectService.findProject(req.getParameter("PrId"), (String) req.getSession().getAttribute("userId"));
        req.setAttribute("project", projectDTO);
        req.getRequestDispatcher("WEB-INF/views/project/projectEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        @NotNull final ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(req.getParameter("idProject"));
        projectDTO.setName(req.getParameter("nameProject"));
        projectDTO.setDescription(req.getParameter("descProject"));
        projectDTO.setDateCreate(new Date());
        projectDTO.setDateStart(ConverterUtil.convertFromStringToDate(req.getParameter("dateStart")));
        projectDTO.setDateFinish(ConverterUtil.convertFromStringToDate(req.getParameter("dateFinish")));
        projectDTO.setStatus(Status.INPROGRESS);
        projectDTO.setUserId((String) req.getSession().getAttribute("userId"));
        projectService.createProject(projectDTO.getId(), projectDTO);
        resp.sendRedirect(req.getContextPath() + "/user");
    }
}