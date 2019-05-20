package ru.khmelev.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.dto.ProjectDTO;

import java.util.Collection;

public interface IProjectService {

    void createProject(@NotNull String id, @NotNull ProjectDTO projectDTO);

    @NotNull ProjectDTO findProject(@NotNull String id, @NotNull String userId);

    @NotNull Collection<ProjectDTO> findAll(@NotNull String userId);

    void editProject(@NotNull String id, @NotNull ProjectDTO projectDTO, @NotNull String userId);

    void removeProject(@NotNull String id, @NotNull String userId);

    void clearProject(@NotNull String userId);
}