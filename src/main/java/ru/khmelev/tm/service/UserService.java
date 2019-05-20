package ru.khmelev.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.api.repository.IUserRepository;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.exception.ServiceException;
import ru.khmelev.tm.repository.UserRepository;
import ru.khmelev.tm.util.PasswordHashUtil;

import java.util.Collection;

public class UserService implements IUserService {

    private static final IUserService _instance = new UserService();

    @NotNull
    private final IUserRepository userRepository = UserRepository.getInstance();

    public static IUserService getInstance() {
        return _instance;
    }

    @Override
    public void createUser(@NotNull final String id, @NotNull final UserDTO userDTO) {
        userRepository.persist(id, userDTO);
    }

    @NotNull
    @Override
    public Collection<UserDTO> findAll() {
        return userRepository.findAll();
    }

    @NotNull
    @Override
    public Collection<UserDTO> findAll(@NotNull final String userId) {
        return findAll();
    }

    @NotNull
    @Override
    public UserDTO findUser(@NotNull final String id) throws ServiceException {
        if (!id.isEmpty()) {
            return userRepository.findOne(id);
        }
        throw new ServiceException();
    }

    @Override
    public void editUser(@NotNull final String id, @NotNull final UserDTO userDTO) {
        if (!id.isEmpty()) {
            userRepository.merge(id, userDTO);
        }
    }

    @Override
    public void removeEntity(@NotNull final String id) {
        if (!id.isEmpty()) {
            userRepository.remove(id);
        }
    }

    @Override
    public void clearEntity() {
        userRepository.removeAll();
    }

    @Nullable
    @Override
    public UserDTO userLogin(@NotNull final String login, @NotNull final String pass) {
        if (!login.isEmpty() && !pass.isEmpty()) {
            for (UserDTO userDTO : findAll()) {
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


//    @NotNull
//    @Override
//    public String getId(@NotNull final UserDTO userDTO) {
//        return userDTO.getId();
//    }
//
//    @NotNull
//    @Override
//    public String getName(@NotNull final UserDTO userDTO) {
//        return userDTO.getLogin();
//    }
//
//    @Override
//    public void userSetPassword(@NotNull final String login, @NotNull final String pass) {
//        if (!login.isEmpty() && !pass.isEmpty()) {
//            for (UserDTO userDTO : userRepository.findAll()) {
//                if (userDTO.getLogin().equals(login)) {
//                    @NotNull final String password = Objects.requireNonNull(PasswordHashUtil.md5(pass));
//                    userDTO.setHashPassword(password);
//                }
//            }
//        }
//    }
//
//    @Override
//    public UserDTO getUserFromSession(@NotNull final String userId) {
//        return userRepository.findOne(userId);
//    }

}