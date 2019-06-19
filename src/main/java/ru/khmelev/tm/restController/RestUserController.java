package ru.khmelev.tm.restController;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.Account;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.enumeration.Role;
import ru.khmelev.tm.util.CustomAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@RestController
public class RestUserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @NotNull
    @PostMapping(value = "/registry", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String registration(@RequestBody @NotNull final Account account) {
        @NotNull final UserDTO newUserDTO = new UserDTO();
        if (!account.getLogin().isEmpty() && !account.getPassword().isEmpty()) {
            newUserDTO.setId(UUID.randomUUID().toString());
            newUserDTO.setLogin(account.getLogin());
            @NotNull final String hashPassword = bCryptPasswordEncoder.encode(account.getPassword());
            newUserDTO.setHashPassword(hashPassword);
            newUserDTO.setRole(Role.ADMIN);
            userService.createUser(newUserDTO.getId(), newUserDTO);
            return newUserDTO.getId();
        }
        return "";
    }

    @Nullable
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean login(HttpServletRequest request, HttpServletResponse response, final HttpSession session, @RequestBody @NotNull final Account account) {

        try {
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(account.getLogin(), account.getPassword());
            token.setDetails(new WebAuthenticationDetails(request));
            final Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            @NotNull final String login = authentication.getName();
            @Nullable final UserDTO userDTO = userService.findByLogin(login);
            if (userDTO != null) {
                request.getSession().setAttribute("userId", userDTO.getId());
            }
        } catch (Exception bd) {
            throw new RuntimeException("Authentication failed for: " + account.getLogin(), bd);
        }
        return true;
    }

    @NotNull
    @GetMapping(value = "/logout")
    public Boolean logout(final HttpSession session) {
        SecurityContextHolder.getContext().setAuthentication(null);
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