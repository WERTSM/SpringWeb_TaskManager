package ru.khmelev.tm.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.khmelev.tm.api.repository.IProjectRepository;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.exception.ServiceException;
import ru.khmelev.tm.repository.ProjectRepository;

import java.util.Collection;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private IProjectRepository projectRepository;

    @Override
    public void createProject(@NotNull final String id, @NotNull final ProjectDTO projectDTO) {
        projectRepository.persist(id, projectDTO);
    }

    @NotNull
    @Override
    public ProjectDTO findProject(@NotNull final String id, @NotNull final String userId) {
        if (!id.isEmpty() && !userId.isEmpty()) {
            return projectRepository.findOne(id, userId);
        }
        throw new ServiceException();
    }

    @NotNull
    @Override
    public Collection<ProjectDTO> findAll(@NotNull final String userId) {
        return projectRepository.findAll(userId);
    }

    @Override
    public void editProject(@NotNull final String id, @NotNull ProjectDTO projectDTO, @NotNull final String userId) {
        if (!id.isEmpty() && !userId.isEmpty()) {
            projectRepository.merge(id, projectDTO, userId);
        }
    }

    @Override
    public void removeProject(@NotNull final String id, @NotNull final String userId) {
        if (!id.isEmpty() && !userId.isEmpty()) {
            projectRepository.remove(id, userId);
        }
    }

    @Override
    public void clearProject(@NotNull final String userId) {
        if (!userId.isEmpty()) {
            projectRepository.removeAll(userId);
        }
    }
}