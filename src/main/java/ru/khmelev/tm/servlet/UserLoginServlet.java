package ru.khmelev.tm.servlet.user;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.service.ProjectService;
import ru.khmelev.tm.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

    @NotNull
    private final IUserService userService = UserService.getInstance();

    @NotNull
    private final IProjectService projectService = ProjectService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/user/userLogin.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @Nullable final UserDTO userDTO = userService.userLogin(req.getParameter("login"), req.getParameter("password"));
        if (userDTO != null) {
            req.getSession().setAttribute("userId", userDTO.getId());
            resp.sendRedirect(req.getContextPath() + "/user");
        } else {
            doGet(req, resp);
        }
    }
}