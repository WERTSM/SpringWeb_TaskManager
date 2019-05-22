package ru.khmelev.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.entity.User;

import java.util.Collection;

public interface IUserRepository {

    void persist(@NotNull final User user);

    @Nullable User findOne(@NotNull final String id);

    @Nullable Collection<User> findAll();

    void merge(@NotNull final User user);

    void remove(@NotNull final User user);

    void removeAll(@NotNull final String userId);
}