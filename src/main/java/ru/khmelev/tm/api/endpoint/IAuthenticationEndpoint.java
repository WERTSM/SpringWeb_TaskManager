package ru.khmelev.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IAuthenticationEndpoint {

    @WebMethod
    Boolean userLogin(
            @WebParam(name = "login") @NotNull final String login,
            @WebParam(name = "password") @NotNull final String password
    );

    @WebMethod
    void userLogOut();
}