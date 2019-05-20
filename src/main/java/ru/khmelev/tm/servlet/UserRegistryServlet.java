package ru.khmelev.tm.servlet;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.enumeration.Role;
import ru.khmelev.tm.service.ProjectService;
import ru.khmelev.tm.service.UserService;
import ru.khmelev.tm.util.PasswordHashUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@WebServlet("/registry")
public class UserRegistryServlet extends HttpServlet {

    @NotNull
    private final IUserService userService = UserService.getInstance();

    @NotNull
    private final IProjectService projectService = ProjectService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        @NotNull final RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/user/userRegistry.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        @NotNull final String login = req.getParameter("login");
        @NotNull final String password = req.getParameter("password");

        if (!login.isEmpty() && !password.isEmpty()) {
            @NotNull final UserDTO userDTO = new UserDTO();
            userDTO.setId(UUID.randomUUID().toString());
            userDTO.setLogin(login);
            @NotNull final String hashPassword = Objects.requireNonNull(PasswordHashUtil.md5(password));
            userDTO.setHashPassword(hashPassword);
            userDTO.setRole(Role.ADMIN);
            userService.createUser(userDTO.getId(), userDTO);
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            doGet(req, resp);
        }
    }
}