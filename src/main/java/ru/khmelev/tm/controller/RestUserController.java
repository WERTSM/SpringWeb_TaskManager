package ru.khmelev.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.Account;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.enumeration.Role;
import ru.khmelev.tm.util.PasswordHashUtil;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

@RestController
public class RestUserController {

    @Autowired
    private IUserService userService;

    @NotNull
    @PostMapping(value = "/registry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO registration(@RequestBody @NotNull final Account account) {
        @NotNull final UserDTO newUserDTO = new UserDTO();
        if (!account.getLogin().isEmpty() && !account.getPassword().isEmpty()) {
            newUserDTO.setId(UUID.randomUUID().toString());
            newUserDTO.setLogin(account.getLogin());
            @NotNull final String hashPassword = Objects.requireNonNull(PasswordHashUtil.md5(account.getPassword()));
            newUserDTO.setHashPassword(hashPassword);
            newUserDTO.setRole(Role.ADMIN);
            userService.createUser(newUserDTO.getId(), newUserDTO);
        }
        return newUserDTO;
    }

    @NotNull
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean login(final HttpSession session, @RequestBody @NotNull final Account account ) {
        @Nullable final UserDTO userDTO = userService.userLogin(account.getLogin(), account.getPassword());
        if (userDTO == null) {
            return false;
        }
        session.setAttribute("userId", userDTO.getId());
        return true;
    }

    @NotNull
    @GetMapping(value = "/logout")
    public Boolean logout(final HttpSession session) {
        session.invalidate();
        return true;
    }

    @Nullable
    @GetMapping(value = "/findUser/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO findUser(@PathVariable(value = "id") @NotNull final String id) {
        @Nullable final UserDTO userDTO = userService.findUser(id);
        return userDTO;
    }

    @NotNull
    @PutMapping(value = "/userEdit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO userEdit(@RequestBody @NotNull final UserDTO userDTO) {
        userService.editUser(userDTO.getId(), userDTO);
        return userDTO;
    }

    @DeleteMapping(value = "/userDelete/{id}")
    public Boolean removeUser(@PathVariable(value = "id") @NotNull final String id) {
        userService.removeUser(id);
        return true;
    }
}