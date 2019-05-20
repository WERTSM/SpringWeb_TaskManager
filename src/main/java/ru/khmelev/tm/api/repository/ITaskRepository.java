package ru.khmelev.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.dto.TaskDTO;

import java.util.Collection;

public interface ITaskRepository {

    void persist(@NotNull String id, @NotNull TaskDTO taskDTO);

    @NotNull TaskDTO findOne(@NotNull String id, @NotNull String userId);

    @NotNull Collection<TaskDTO> findAll(@NotNull String userId);

    void merge(@NotNull String id, @NotNull TaskDTO taskDTO, @NotNull String userId);

    void remove(@NotNull String id, @NotNull String userId);

    void removeAll(@NotNull String userId);
}