package ru.khmelev.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.dto.UserDTO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public interface IUserEndpoint {

    @WebMethod
    void createUser(
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "user") @NotNull final UserDTO userDTO
    );

    @NotNull
    @WebMethod
    Collection<UserDTO> findAllUser();

    @Nullable
    @WebMethod
    UserDTO findUser(
            @WebParam(name = "id") @NotNull final String id
    );

    @WebMethod
    void editUser(
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "user") @NotNull final UserDTO userDTO
    );

    @WebMethod
    void removeUser(
            @WebParam(name = "id") @NotNull final String id
    );

    @WebMethod
    Boolean userLogin(
            @WebParam(name = "login") @NotNull final String login,
            @WebParam(name = "password") @NotNull final String password
    );

    @WebMethod
    void userLogOut();
}