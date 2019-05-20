package ru.khmelev.tm.servlet;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.enumeration.Role;
import ru.khmelev.tm.service.UserService;
import ru.khmelev.tm.util.PasswordHashUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/userEdit")
public class UserEditServlet extends HttpServlet {

    @NotNull
    private final IUserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/user/userEdit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        @NotNull final UserDTO userDTO = new UserDTO();
        userDTO.setId((String) req.getSession().getAttribute("userId"));
        @NotNull final String hashPassword = Objects.requireNonNull(PasswordHashUtil.md5(req.getParameter("password")));
        userDTO.setHashPassword(hashPassword);
        userDTO.setLogin(req.getParameter("login"));
        userDTO.setRole(Role.ADMIN);
        userService.createUser(userDTO.getId(), userDTO);
        resp.sendRedirect(req.getContextPath() + "/logout");
    }
}