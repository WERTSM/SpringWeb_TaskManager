package ru.khmelev.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.dto.ProjectDTO;

import java.util.Collection;

public interface IProjectRepository {

    void persist(@NotNull String id, @NotNull ProjectDTO projectDTO);

    @NotNull ProjectDTO findOne(@NotNull String id, @NotNull String userId);

    @NotNull Collection<ProjectDTO> findAll(@NotNull String userId);

    void merge(@NotNull String id, @NotNull ProjectDTO projectDTO, @NotNull String userId);

    void remove(@NotNull String id, @NotNull String userId);

    void removeAll(@NotNull String userId);
}