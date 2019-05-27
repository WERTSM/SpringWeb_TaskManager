package ru.khmelev.tm.controller;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.enumeration.Role;
import ru.khmelev.tm.service.UserService;
import ru.khmelev.tm.util.PasswordHashUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ManagedBean
@RequestScoped
@URLMappings(
        mappings = {
                @URLMapping(id = "userLogin", pattern = "/userLogin", viewId = "/WEB-INF/views/user/user-login.xhtml")
        })
public class UserController extends SpringBeanAutowiringSupport {

    @Autowired
    private IUserService userService;

    private String login;

    private String password;

    @NotNull
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    @NotNull
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    @NotNull
    private
    String userId = (String) session.getAttribute("userId");

    public String login1() {
        System.out.println("~!!!!!!!!!!!!!");
        @Nullable final UserDTO userDTO = userService.userLogin(login, password);
        if (userDTO != null) {
            session.setAttribute("userId", userDTO.getId());
            return "/WEB-INF/views/project/project-list.xhtml?faces-redirect=true";
        } else {
            return "/WEB-INF/views/user/user-login.xhtml?faces-redirect=true";
        }
    }

    public String logout(final HttpSession session) {
        session.invalidate();
        return "user/user-login";
    }

    @GetMapping("/user")
    public String userPage(final HttpSession session, final Model model) {
        @NotNull final String userId = (String) session.getAttribute("userId");
        @NotNull final UserDTO userDTO = Objects.requireNonNull(userService.findUser(userId));
        model.addAttribute("userDTO", userDTO);
        return ("user/user-page");
    }

    @GetMapping("/userEdit")
    public String userEditPage() {
        return ("user/user-edit");
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
        userService.editUser(userDTO.getId(), userDTO);
        return "redirect:/logout";
    }

    @GetMapping("/registry")
    public String registryPage() {
        return "user/user-registry";
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
            return "user/user-registry";
        }
    }
}