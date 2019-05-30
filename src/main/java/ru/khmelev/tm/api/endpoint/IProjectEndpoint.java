package ru.khmelev.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.dto.ProjectDTO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public interface IProjectEndpoint {

    @WebMethod
    void createProject(
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "project") @NotNull final ProjectDTO projectDTO
    );

    @Nullable
    @WebMethod
    ProjectDTO findProject(
            @WebParam(name = "id") @NotNull final String id
    );

    @NotNull
    @WebMethod
    Collection<ProjectDTO> findAllProject();

    @WebMethod
    void editProject(
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "project") @NotNull final ProjectDTO projectDTO
    );

    @WebMethod
    void removeProject(
            @WebParam(name = "id") @NotNull final String id
    );

    @WebMethod
    void clearProject();

    @WebMethod
    void testSoapProject(@WebParam(name = "project") @NotNull final String project
    );
}