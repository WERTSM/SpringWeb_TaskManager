package ru.khmelev.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.dto.ProjectDTO;

import java.util.Collection;

public interface IProjectService {

    void createProject(@NotNull String id, @NotNull final ProjectDTO projectDTO);

    void editProject(@NotNull final String id, @NotNull final ProjectDTO projectDTO, @NotNull final String userId);

    @Nullable ProjectDTO findProject(@NotNull final String id, @NotNull final String userId);

    @NotNull Collection<ProjectDTO> findAll(@NotNull final String userId);

    @NotNull Collection<ProjectDTO> findAllName(@NotNull final String findParameter, @NotNull final String userId);

    @NotNull Collection<ProjectDTO> findAllDescription(@NotNull final String findParameter, @NotNull final String userId);

    void removeProject(@NotNull final String id, @NotNull final String userId);

    void clearProject(@NotNull final String userId);
}