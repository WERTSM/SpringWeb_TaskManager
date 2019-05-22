package ru.khmelev.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khmelev.tm.api.repository.IUserRepository;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.entity.User;
import ru.khmelev.tm.exception.ServiceException;
import ru.khmelev.tm.util.PasswordHashUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private
    IUserRepository userRepository;

    @Override
    public void createUser(@NotNull final String id, @NotNull final UserDTO userDTO) {
        @NotNull final User user = new User();
        user.setId(id);
        fromDTOToUser(userDTO, user);

        userRepository.persist(user);
    }

    @Nullable
    @Override
    public UserDTO findUser(@NotNull final String id) {
        if (id.isEmpty()) throw new ServiceException();

        @NotNull final User user = Objects.requireNonNull(userRepository.findOne(id));
        return fromUserToDTO(user);
    }

    @Nullable
    @Override
    public Collection<UserDTO> findAll() {
        @NotNull final Collection<User> list = Objects.requireNonNull(userRepository.findAll());
        @NotNull final List<UserDTO> listDTO = new ArrayList<>();
        for (User user : list) {
            listDTO.add(fromUserToDTO(user));
        }
        return listDTO;
    }

    @Override
    public void editUser(@NotNull final String id, @NotNull UserDTO userDTO) {
        if (id.isEmpty()) throw new ServiceException();

        @NotNull final User user = Objects.requireNonNull(userRepository.findOne(id));
        userRepository.merge(fromDTOToUser(userDTO, user));
    }

    @Override
    public void removeUser(@NotNull final String id) {
        if (id.isEmpty()) throw new ServiceException();

        userRepository.remove(Objects.requireNonNull(userRepository.findOne(id)));
    }

    @NotNull
    @Override
    public String getId(@NotNull final UserDTO userDTO) {
        return userDTO.getId();
    }

    @NotNull
    @Override
    public String getName(@NotNull final UserDTO userDTO) {
        return userDTO.getLogin();
    }

    @Override
    public void userSetPassword(@NotNull final String login, @NotNull final String pass) {
        if (login.isEmpty() || pass.isEmpty()) throw new ServiceException();

        @NotNull final Collection<UserDTO> users = findAll();
        for (UserDTO userDTO : users) {
            if (userDTO.getLogin().equals(login)) {
                @NotNull final String password = Objects.requireNonNull(PasswordHashUtil.md5(pass));
                userDTO.setHashPassword(password);
                editUser(userDTO.getId(), userDTO);
            }
        }
    }

    @Nullable
    @Override
    public UserDTO getUserFromSession(@NotNull final String userId) {
        return findUser(userId);
    }

    @NotNull
    private User fromDTOToUser(@NotNull final UserDTO userDTO, @NotNull final User user) {
        user.setLogin(userDTO.getLogin());
        user.setHashPassword(userDTO.getHashPassword());
        user.setRole(userDTO.getRole());
        return user;
    }

    @NotNull
    private UserDTO fromUserToDTO(@NotNull final User user) {
        @NotNull final UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        userDTO.setHashPassword(user.getHashPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    @Nullable
    @Override
    public UserDTO userLogin(@NotNull final String login, @NotNull final String pass) {
        if (!login.isEmpty() && !pass.isEmpty()) {
            for (final UserDTO userDTO : Objects.requireNonNull(findAll())) {
                if (userDTO.getLogin().equals(login)) {
                    String password = PasswordHashUtil.md5(pass);
                    String passwordUserRepository = userDTO.getHashPassword();
                    if (passwordUserRepository.equals(password)) {
                        return userDTO;
                    }
                }
            }
        }
        return null;
    }
}