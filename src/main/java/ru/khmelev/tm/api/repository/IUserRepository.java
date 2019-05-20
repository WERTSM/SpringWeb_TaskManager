package ru.khmelev.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.dto.UserDTO;

import java.util.Collection;

public interface IUserRepository {

    void persist(@NotNull String id, @NotNull UserDTO sessionDTO);

    @NotNull UserDTO findOne(@NotNull String id);

    @NotNull Collection<UserDTO> findAll();

    void merge(@NotNull String id, @NotNull UserDTO sessionDTO);

    void remove(@NotNull String id);

    void removeAll();
}