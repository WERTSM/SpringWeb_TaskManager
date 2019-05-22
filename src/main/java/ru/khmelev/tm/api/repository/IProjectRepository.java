package ru.khmelev.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.entity.Project;

import java.util.Collection;

public interface IProjectRepository {

    void persist(@NotNull final Project project);

    @Nullable Project findOne(@NotNull final String id, @NotNull final String userId);

    @Nullable Collection<Project> findAll(@NotNull final String userId);

    void merge(@NotNull final Project project);

    void remove(@NotNull final Project project);

    void removeAll(@NotNull final String userId);
}