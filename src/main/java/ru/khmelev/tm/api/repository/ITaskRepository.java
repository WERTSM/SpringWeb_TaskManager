package ru.khmelev.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.entity.Task;

import java.util.Collection;

public interface ITaskRepository {

    void persist(@NotNull final Task task);

    @Nullable Task findOne(@NotNull final String id, @NotNull final String userId);

    @Nullable Collection<Task> findAll(@NotNull final String userId);

    void merge(@NotNull final Task task);

    void remove(@NotNull final Task task);

    void removeAll(@NotNull final String userId);

    @Nullable Collection<Task> findAllTaskFromProject(@NotNull final String projectId, @NotNull final String userId);
}