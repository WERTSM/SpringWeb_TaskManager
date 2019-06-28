package ru.khmelev.tm.endpoint;

import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.khmelev.tm.api.endpoint.IProjectEndpoint;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.dto.ProjectDTO;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;

@Component
@WebService(endpointInterface = "ru.khmelev.tm.api.endpoint.IProjectEndpoint")
public class ProjectEndpoint implements IProjectEndpoint {

    @Autowired
    private IProjectService projectService;

    @Override
    @WebMethod
    public void createProject(@NotNull String id, @NotNull ProjectDTO projectDTO) {
        projectService.createProject(id, projectDTO);
    }

    @Override
    @WebMethod
    public ProjectDTO findProject(@WebParam(name = "id") @NotNull final String id) {
        return projectService.findProject(id, getUserId());
    }

    @WebMethod
    @NotNull
    @Override
    public Collection<ProjectDTO> findAllProject() {
        return projectService.findAll(getUserId());
    }

    @WebMethod
    @Override
    public void editProject(
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "project") @NotNull ProjectDTO projectDTO
    ) {
        projectService.editProject(id, projectDTO, getUserId());
    }

    @WebMethod
    @Override
    public void removeProject(
            @WebParam(name = "id") @NotNull final String id
    ) {
        projectService.removeProject(id, getUserId());
    }

    @WebMethod
    @Override
    public void clearProject() {
        projectService.clearProject(getUserId());
    }

    private String getUserId() {
        final Message message = PhaseInterceptorChain.getCurrentMessage();
        final HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
        final HttpSession session = request.getSession(true);
        return (String) session.getAttribute("userId");
    }

    @Override
    @WebMethod
    public void testSoapProject(@WebParam(name = "id") @NotNull final String id
    ) {
        System.out.println("SOAP РАБОТАЕТ!!!! пришедшее значение в эндпоинте-проекте: " + id);
    }
}