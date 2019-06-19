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
import ru.khmelev.tm.api.endpoint.IUserEndpoint;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@WebService(endpointInterface = "ru.khmelev.tm.api.endpoint.IUserEndpoint")
public class UserEndpoint implements IUserEndpoint {

    @Autowired
    private IUserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    @WebMethod
    public void createUser(
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "user") @NotNull final UserDTO user
    ) {
        userService.createUser(id, user);
    }

    @NotNull
    @Override
    @WebMethod
    public Collection<UserDTO> findAllUser() {
        return userService.findAll();
    }

    @Override
    @Nullable
    @WebMethod
    public UserDTO findUser(
            @WebParam(name = "id") @NotNull final String id
    ) {
        return userService.findUser(id);
    }

    @Override
    @WebMethod
    public void editUser(
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "user") @NotNull UserDTO user
    ) {
        userService.editUser(id, user);
    }

    @Override
    @WebMethod
    public void removeUser(
            @WebParam(name = "id") @NotNull final String id
    ) {
        userService.removeUser(id);
    }

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