package ru.khmelev.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.dto.TaskDTO;

import java.util.Collection;

public interface ITaskService {

    void createTask(@NotNull String id, @NotNull TaskDTO taskDTO);

    @NotNull TaskDTO findTask(@NotNull String id, @NotNull String userId);

    @NotNull Collection<TaskDTO> findAll(@NotNull String userId);

    void editTask(@NotNull String id, @NotNull TaskDTO taskDTO, @NotNull String userId);

    void removeTask(@NotNull String id, @NotNull String userId);

    void clearTask(@NotNull String userId);

    Collection<TaskDTO> listTaskFromProject(@NotNull final String projectId, @NotNull final String userId);

    void removeAllTaskFromProject(@NotNull String projectId, @NotNull String userId);
}