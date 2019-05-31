package ru.khmelev.tm.endpoint;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserDTO userLogin(
            @WebParam(name = "login") @NotNull final String login,
            @WebParam(name = "password") @NotNull final String pass
    ) {
        @Nullable final UserDTO userDTO = userService.userLogin(login, pass);
        if (userDTO != null) {
            final Message message = PhaseInterceptorChain.getCurrentMessage();
            final HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
            final HttpSession session = request.getSession(true);
            session.setAttribute("userId", userDTO.getId());
            return userDTO;
        } else return null;
    }

    @Override
    @WebMethod
    public void userLogOut() {
        final Message message = PhaseInterceptorChain.getCurrentMessage();
        final HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        final HttpSession session = request.getSession(true);
        System.out.println("SOAP проверка! Юзер вышел с таким айди: " + session.getAttribute("userId"));
        session.invalidate();
    }

    @Override
    @WebMethod
    public void testSoapUser(@WebParam(name = "id") @NotNull final String id
    ) {
        System.out.println("SOAP РАБОТАЕТ!!!! пришедшее значение в эндпоинте-юзере: " + id);
    }
}