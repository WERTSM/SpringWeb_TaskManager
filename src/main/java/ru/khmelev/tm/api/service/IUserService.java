package ru.khmelev.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.exception.ServiceException;

import java.util.Collection;

public interface IUserService {

    void createUser(@NotNull String id, @NotNull UserDTO userDTO);

    @NotNull Collection<UserDTO> findAll();

    @NotNull Collection<UserDTO> findAll(@NotNull String userId);

    @NotNull UserDTO findUser(@NotNull String id) throws ServiceException;

    void editUser(@NotNull String id, @NotNull UserDTO userDTO);

    void removeEntity(@NotNull String id);

    void clearEntity();

    @Nullable UserDTO userLogin(@NotNull String login, @NotNull String pass);

//    @NotNull String getId(@NotNull final UserDTO user);
//
//    @NotNull String getName(@NotNull final UserDTO user);
//
//    void userSetPassword(@NotNull final String login, @NotNull final String pass);
//
//    UserDTO getUserDTOFromSession(@NotNull final String userId);
}