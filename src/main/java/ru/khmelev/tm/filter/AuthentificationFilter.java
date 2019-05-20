package ru.khmelev.tm.filter;

import org.jetbrains.annotations.NotNull;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/projects", "/projectCreate", "/projectEdit", "/projectDelete", "/tasks", "/taskCreate", "/taskEdit", "/taskDelete", "/user", "/userEdit", "/logout"})
public class AuthentificationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        @NotNull final HttpServletRequest request = (HttpServletRequest) servletRequest;
        @NotNull final HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getSession().getAttribute("userId") == null) {
            response.sendRedirect("/login");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}