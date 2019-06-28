package ru.khmelev.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khmelev.tm.api.repository.IUserRepository;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.entity.User;
import ru.khmelev.tm.exception.ServiceException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void createUser(@NotNull final String id, @NotNull final UserDTO userDTO) {
        @NotNull final User user = new User();
        user.setId(id);
        fromDTOToUser(userDTO, user);

        userRepository.save(user);
    }

    @Nullable
    @Override
    public UserDTO findUser(@NotNull final String id) {
        if (id.isEmpty()) throw new ServiceException();

        @Nullable final User user = userRepository.findOne(id);
        return fromUserToDTO(user);
    }

    @NotNull
    @Override
    public Collection<UserDTO> findAll() {
        @Nullable final Collection<User> list = userRepository.findAll();
        @NotNull final List<UserDTO> listDTO = new ArrayList<>();
        for (User user : Objects.requireNonNull(list)) {
            listDTO.add(fromUserToDTO(user));
        }
        return listDTO;
    }

    @Nullable
    @Override
    public UserDTO findByLogin(@NotNull final String login) {
        if (login.isEmpty()) throw new ServiceException();

        @Nullable final User user = userRepository.findByLogin(login);
        return fromUserToDTO(user);
    }

    @Override
    public void editUser(@NotNull final String id, @NotNull UserDTO userDTO) {
        if (id.isEmpty()) throw new ServiceException();

        @Nullable final User user = userRepository.findOne(id);
        userRepository.save(fromDTOToUser(userDTO, Objects.requireNonNull(user)));
    }

    @Override
    public void removeUser(@NotNull final String id) {
        if (id.isEmpty()) throw new ServiceException();

        userRepository.delete(Objects.requireNonNull(userRepository.findOne(id)));
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

        @Nullable final Collection<UserDTO> users = findAll();
        for (UserDTO userDTO : users) {
            if (userDTO.getLogin().equals(login)) {
                @NotNull final String password = Objects.requireNonNull(bCryptPasswordEncoder.encode(pass));
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

    @Nullable
    private UserDTO fromUserToDTO(@Nullable final User user) {
        if (user == null) {
            return null;
        }
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
                    String password = bCryptPasswordEncoder.encode(pass);
                    String passwordUserRepository = userDTO.getHashPassword();
                    if (passwordUserRepository.equals(password)) {
                        return userDTO;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        @Nullable final User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Нету юзера");
        }
        @NotNull final List<GrantedAuthority> listRole = new ArrayList<>();
        listRole.add(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getHashPassword(), listRole);
    }
}