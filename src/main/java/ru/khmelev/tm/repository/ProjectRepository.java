package ru.khmelev.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.api.repository.IProjectRepository;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.exception.RepositoryException;

import java.util.*;

public class ProjectRepository implements IProjectRepository {

    private static final IProjectRepository _instance = new ProjectRepository();

    private static Map<String, ProjectDTO> projectMap = new HashMap<>();

    public static IProjectRepository getInstance() {
        return _instance;
    }

    @Override
    public void persist(@NotNull final String id, @NotNull final ProjectDTO projectDTO) {
        if (!id.isEmpty()) {
            projectMap.put(id, projectDTO);
        }
    }

    @NotNull
    @Override
    public ProjectDTO findOne(@NotNull final String id, @NotNull final String userId) {
        if (!id.isEmpty() && !userId.isEmpty()) {
            final Collection<ProjectDTO> list = new ArrayList<>(findAll(userId));
            for (ProjectDTO projectDTO : list) {
                if (projectDTO.getId().equals(id)) {
                    return projectDTO;
                }
            }
        }
        throw new RepositoryException();
    }

    @NotNull
    @Override
    public Collection<ProjectDTO> findAll(@NotNull final String userId) {
        if (!userId.isEmpty()) {
            Collection<ProjectDTO> list = new ArrayList<>(projectMap.values());
            Iterator<ProjectDTO> it = list.iterator();
            while (it.hasNext()) {
                ProjectDTO projectDTO = it.next();
                if (!projectDTO.getUserId().equals(userId)) {
                    it.remove();
                }
            }
            return list;
        }
        throw new RepositoryException();
    }

    @Override
    public void merge(@NotNull final String id, @NotNull final ProjectDTO projectDTO, @NotNull final String userId) {
    }


    @Override
    public void remove(@NotNull final String id, @NotNull final String userId) {
        if (!id.isEmpty() && !userId.isEmpty()) {
            Collection<ProjectDTO> list = projectMap.values();
            Iterator<ProjectDTO> it = list.iterator();
            while (it.hasNext()) {
                ProjectDTO projectDTO = it.next();
                if (projectDTO.getId().equals(id) && projectDTO.getUserId().equals(userId)) {
                    it.remove();
                    return;
                }
            }
        }
    }

    @Override
    public void removeAll(@NotNull final String userId) {
        if (!userId.isEmpty()) {
            Collection<ProjectDTO> list = projectMap.values();
            list.clear();
        }
    }
}