package ru.khmelev.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.khmelev.tm.api.endpoint.IUserEndpoint;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@Component
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
}