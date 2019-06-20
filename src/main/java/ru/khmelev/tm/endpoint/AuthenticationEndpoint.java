package ru.khmelev.tm.endpoint;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.khmelev.tm.api.endpoint.IAuthenticationEndpoint;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebService(endpointInterface = "ru.khmelev.tm.api.endpoint.IAuthenticationEndpoint")
public class AuthenticationEndpoint implements IAuthenticationEndpoint {

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @WebMethod
    public Boolean userLogin(
            @WebParam(name = "login") @NotNull final String login,
            @WebParam(name = "password") @NotNull final String pass
    ) {

        try {
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(login, pass);

            final Message message = PhaseInterceptorChain.getCurrentMessage();
            final HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
            final HttpSession session = request.getSession(true);
            final Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            @NotNull final String login2 = authentication.getName();
            @Nullable final UserDTO userDTO = userService.findByLogin(login2);
            if (userDTO != null) {
                session.setAttribute("userId", userDTO.getId());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Authentication failed for: " + login, ex);
        }
        return true;
    }

    @Override
    @WebMethod
    public void userLogOut() {
        final Message message = PhaseInterceptorChain.getCurrentMessage();
        final HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        final HttpSession session = request.getSession(true);
        session.invalidate();
    }
}