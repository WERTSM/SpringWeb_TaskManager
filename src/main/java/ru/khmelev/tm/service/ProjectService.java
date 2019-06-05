package ru.khmelev.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khmelev.tm.api.repository.IProjectRepository;
import ru.khmelev.tm.api.repository.IUserRepository;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.entity.Project;
import ru.khmelev.tm.exception.ServiceException;

import java.util.*;

@Service
@Transactional
public class ProjectService implements IProjectService {

    @Autowired
    private
    IProjectRepository projectRepository;

    @Autowired
    private
    IUserRepository userRepository;

    @Override
    public void createProject(@NotNull final String id, @NotNull final ProjectDTO projectDTO) {
        @NotNull final Project project = new Project();
        project.setId(id);
        fromDTOToProject(projectDTO, project);

        projectRepository.save(project);
    }

    @Nullable
    @Override
    public ProjectDTO findProject(@NotNull final String id, @NotNull final String userId) {
        if (userId.isEmpty()) throw new ServiceException();

        @Nullable final Project project = projectRepository.findOne(id, userId);
        if (project != null) {
            return fromProjectToDTO(project);
        }
        return null;
    }

    @NotNull
    @Override
    public Collection<ProjectDTO> findAll(@NotNull final String userId) {
        if (userId.isEmpty()) throw new ServiceException();

        @NotNull final Collection<Project> list = Objects.requireNonNull(projectRepository.findAll(userId));
        @NotNull final List<ProjectDTO> listDTO = new ArrayList<>();
        for (Project pr : list) {
            listDTO.add(fromProjectToDTO(pr));
        }
        return listDTO;
    }

    @Override
    public void editProject(@NotNull final String id, @NotNull ProjectDTO projectDTO, @NotNull final String userId) {
        if (id.isEmpty() || userId.isEmpty()) throw new ServiceException();

        @Nullable final Project project = projectRepository.findOne(id, userId);
        assert project != null;
        projectRepository.save(fromDTOToProject(projectDTO, project));
    }

    @NotNull
    @Override
    public Collection<ProjectDTO> findAllName(@NotNull final String findParameter, @NotNull final String userId) {
        if (findParameter.isEmpty() || userId.isEmpty()) throw new ServiceException();

        @NotNull final List<Project> list = new ArrayList<>(Objects.requireNonNull(projectRepository.findAll(userId)));
        @NotNull final Iterator<Project> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getName().contains(findParameter)) {
                iterator.remove();
            }
        }
        @NotNull final List<ProjectDTO> listDTO = new ArrayList<>();
        for (Project pr : list) {
            listDTO.add(fromProjectToDTO(pr));
        }
        return listDTO;
    }

    @NotNull
    @Override
    public Collection<ProjectDTO> findAllDescription(@NotNull final String findParameter, @NotNull final String userId) {
        if (findParameter.isEmpty() || userId.isEmpty()) throw new ServiceException();

        @NotNull final List<Project> list = new ArrayList<>(Objects.requireNonNull(projectRepository.findAll(userId)));
        @NotNull final Iterator<Project> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getDescription().contains(findParameter)) {
                iterator.remove();
            }
        }
        @NotNull final List<ProjectDTO> listDTO = new ArrayList<>();
        for (Project pr : list) {
            listDTO.add(fromProjectToDTO(pr));
        }
        return listDTO;
    }

    @Override
    public void removeProject(@NotNull final String id, @NotNull final String userId) {
        if (id.isEmpty() || userId.isEmpty()) throw new ServiceException();

        projectRepository.delete(Objects.requireNonNull(projectRepository.findOne(id, userId)));
    }

    @Override
    public void clearProject(@NotNull final String userId) {
        if (userId.isEmpty()) throw new ServiceException();

        projectRepository.removeAll(userId);
    }

    @NotNull
    private Project fromDTOToProject(@NotNull final ProjectDTO projectDTO, @NotNull final Project project) {
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setDateStart(projectDTO.getDateStart());
        project.setDateFinish(projectDTO.getDateFinish());
        project.setDateCreate(projectDTO.getDateCreate());
        project.setStatus(projectDTO.getStatus());
        project.setUser(userRepository.findOne(projectDTO.getUserId()));
        return project;
    }

    @NotNull
    private ProjectDTO fromProjectToDTO(@NotNull final Project project) {
        @NotNull final ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        projectDTO.setDescription(project.getDescription());
        projectDTO.setDateStart(project.getDateStart());
        projectDTO.setDateFinish(project.getDateFinish());
        projectDTO.setDateCreate(project.getDateCreate());
        projectDTO.setStatus(project.getStatus());
        projectDTO.setUserId(project.getUser().getId());
        return projectDTO;
    }
}