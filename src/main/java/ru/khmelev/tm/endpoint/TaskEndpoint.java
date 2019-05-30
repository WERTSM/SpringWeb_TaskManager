package ru.khmelev.tm.endpoint;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ru.khmelev.tm.api.endpoint.ITaskEndpoint;
import ru.khmelev.tm.api.service.ITaskService;
import ru.khmelev.tm.dto.TaskDTO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;


@WebService(endpointInterface = "ru.khmelev.tm.api.endpoint.ITaskEndpoint")
public class TaskEndpoint implements ITaskEndpoint {

    @Autowired
    private ITaskService taskService;

    @Override
    @WebMethod
    public void createTask(
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "task") @NotNull final TaskDTO taskDTO
    ) {
        taskService.createTask(id, taskDTO);
    }

    @Override
    @WebMethod
    public TaskDTO findTask(
            @WebParam(name = "id") @NotNull final String id
    ) {
        return taskService.findTask(id, getUserId());
    }

    @NotNull
    @Override
    @WebMethod
    public Collection<TaskDTO> findAllTask() {
        return taskService.findAll(getUserId());
    }

    @Override
    @WebMethod
    public void editTask(
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "task") @NotNull TaskDTO taskDTO
    ) {
        taskService.editTask(id, taskDTO, getUserId());
    }

    @Override
    @WebMethod
    public void removeTask(
            @WebParam(name = "id") @NotNull final String id
    ) {
        taskService.removeTask(id, getUserId());
    }

    @Override
    @WebMethod
    public void clearTask() {
        taskService.clearTask(getUserId());
    }

    @NotNull
    @Override
    @WebMethod
    public Collection<TaskDTO> listTaskFromProject(
            @WebParam(name = "projectId") @NotNull final String projectId
    ) {
        return taskService.listTaskFromProject(projectId, getUserId());
    }

    private String getUserId() {
        final Message message = PhaseInterceptorChain.getCurrentMessage();
        final HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        final HttpSession session = request.getSession(true);
        return (String) session.getAttribute("userId");
    }

    @Override
    @WebMethod
    public void testSoapTask(@WebParam(name = "id") @NotNull final String id
    ) {
        System.out.println("SOAP РАБОТАЕТ!!!! пришедшее значение в эндпоинте-задаче: " + id);
    }
}