package ru.khmelev.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.dto.TaskDTO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public interface ITaskEndpoint {

    @WebMethod
    void createTask(
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "task") @NotNull final TaskDTO taskDTO
    );

    @Nullable
    @WebMethod
    TaskDTO findTask(
            @WebParam(name = "id") @NotNull final String id
    );

    @NotNull
    @WebMethod
    Collection<TaskDTO> findAllTask();

    @WebMethod
    void editTask(
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "task") @NotNull final TaskDTO taskDTO
    );

    @WebMethod
    void removeTask(
            @WebParam(name = "id") @NotNull final String id
    );

    @WebMethod
    void clearTask();

    @NotNull
    @WebMethod
    Collection<TaskDTO> listTaskFromProject(
            @WebParam(name = "projectId") @NotNull final String projectId
    );

    @WebMethod
    void testSoapTask(@WebParam(name = "id") @NotNull final String id
    );
}