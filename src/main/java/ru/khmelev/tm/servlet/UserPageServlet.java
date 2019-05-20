package ru.khmelev.tm.servlet;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserPageServlet extends HttpServlet {
    @NotNull
    private final IUserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @NotNull final String userId = (String) req.getSession().getAttribute("userId");
        @NotNull final UserDTO userDTO = userService.findUser(userId);
        req.setAttribute("userDTO", userDTO);
        @NotNull final RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/userPage.jsp");
        requestDispatcher.forward(req, resp);
    }
}