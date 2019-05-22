package ru.khmelev.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.dto.TaskDTO;

import java.util.Collection;

public interface ITaskService {

    void createTask(@NotNull String id, @NotNull final TaskDTO taskDTO);

    void editTask(@NotNull final String id, @NotNull final TaskDTO taskDTO, @NotNull final String userId);

    @Nullable TaskDTO findTask(@NotNull final String id, @NotNull final String userId);

    @Nullable Collection<TaskDTO> findAll(@NotNull final String userId);

    @Nullable Collection<TaskDTO> findAllName(@NotNull final String findParameter, @NotNull final String userId);

    @Nullable Collection<TaskDTO> findAllDescription(@NotNull final String findParameter, @NotNull final String userId);

    void removeTask(@NotNull final String id, @NotNull final String userId);

    void clearTask(@NotNull final String userId);

    @Nullable Collection<TaskDTO> listTaskFromProject(@NotNull final String projectId, @NotNull final String userId);
}