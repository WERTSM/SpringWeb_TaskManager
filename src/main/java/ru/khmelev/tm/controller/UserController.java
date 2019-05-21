package ru.khmelev.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.enumeration.Role;
import ru.khmelev.tm.util.PasswordHashUtil;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "user/userLogin";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("login") final String login,
            @RequestParam("password") final String password,
            final HttpSession session
    ) {
        @Nullable final UserDTO userDTO = userService.userLogin(login, password);
        if (userDTO != null) {
            session.setAttribute("userId", userDTO.getId());
            return "redirect:/user";
        } else {
            return "user/userLogin";
        }
    }

    @GetMapping("/logout")
    public String logout(final HttpSession session) {
        session.invalidate();
        return "user/userLogin";
    }

    @GetMapping("/user")
    public String userPage(final HttpSession session, final Model model) {
        @NotNull final String userId = (String) session.getAttribute("userId");
        @NotNull final UserDTO userDTO = userService.findUser(userId);
        model.addAttribute("userDTO", userDTO);
        return ("user/userPage");
    }

    @GetMapping("/userEdit")
    public String userEditPage() {
        return ("user/userEdit");
    }

    @PostMapping("/userEdit")
    public String userEdit(
            final HttpSession session,
            @RequestParam("login") final String login,
            @RequestParam("password") final String password
    ) {
        @NotNull final UserDTO userDTO = new UserDTO();
        userDTO.setId((String) session.getAttribute("userId"));
        @NotNull final String hashPassword = Objects.requireNonNull(PasswordHashUtil.md5(password));
        userDTO.setHashPassword(hashPassword);
        userDTO.setLogin(login);
        userDTO.setRole(Role.ADMIN);
        userService.createUser(userDTO.getId(), userDTO);
        return "redirect:/logout";
    }

    @GetMapping("/registry")
    public String registryPage() {
        return "user/userRegistry";
    }

    @PostMapping("/registry")
    public String registration(
            @RequestParam("login") final String login,
            @RequestParam("password") final String password,
            final HttpSession session
    ) {
        if (!login.isEmpty() && !password.isEmpty()) {
            @NotNull final UserDTO userDTO = new UserDTO();
            userDTO.setId(UUID.randomUUID().toString());
            userDTO.setLogin(login);
            @NotNull final String hashPassword = Objects.requireNonNull(PasswordHashUtil.md5(password));
            userDTO.setHashPassword(hashPassword);
            userDTO.setRole(Role.ADMIN);
            userService.createUser(userDTO.getId(), userDTO);
            return "redirect:/login";
        } else {
            return "user/userRegistry";
        }
    }
}