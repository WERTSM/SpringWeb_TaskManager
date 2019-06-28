package ru.khmelev.tm.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private IUserService userService;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {
        @NotNull final String login = authentication.getName();
        @Nullable final UserDTO userDTO = userService.findByLogin(login);
        if (userDTO != null) {
            request.getSession().setAttribute("userId", userDTO.getId());
            System.out.println(userDTO.getId());
        }
        if (!request.getHeader("referer").contains("userLogin")) {
            response.sendRedirect(request.getHeader("referer"));
            System.out.println("ПИЗДА"+request.getHeader("referer"));
        } else {
            response.sendRedirect(request.getContextPath() + "/projectList");
        }
    }
}