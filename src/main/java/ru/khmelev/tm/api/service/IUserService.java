package ru.khmelev.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.dto.UserDTO;

import java.util.Collection;

public interface IUserService {

    void createUser(@NotNull final String id, @NotNull final UserDTO userDTO);

    @Nullable UserDTO findUser(@NotNull final String id);

    @NotNull Collection<UserDTO> findAll();

    void editUser(@NotNull final String id, @NotNull final UserDTO userDTO);

    void removeUser(@NotNull final String id);

    @NotNull String getId(@NotNull final UserDTO user);

    @NotNull String getName(@NotNull final UserDTO user);

    void userSetPassword(@NotNull final String login, @NotNull final String pass);

    @Nullable UserDTO getUserFromSession(@NotNull final String userId);

    @Nullable UserDTO userLogin(@NotNull String login, @NotNull String pass);
}