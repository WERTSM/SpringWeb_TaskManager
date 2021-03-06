package ru.khmelev.tm.controller;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.khmelev.tm.util.ViewScope;
import ru.khmelev.tm.api.service.IUserService;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.enumeration.Role;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Component
@Scope(ViewScope.NAME)
@URLMappings(
        mappings = {
                @URLMapping(id = "userRegistry", pattern = "/userRegistry", viewId = "/WEB-INF/views/user/user-registry.xhtml"),
                @URLMapping(id = "userPage", pattern = "/userPage", viewId = "/WEB-INF/views/user/user-page.xhtml"),
                @URLMapping(id = "userLogin", pattern = "/userLogin", viewId = "/WEB-INF/views/user/user-login.xhtml"),
                @URLMapping(id = "userEdit", pattern = "/userEdit", viewId = "/WEB-INF/views/user/user-edit.xhtml")
        })
public class UserController extends SpringBeanAutowiringSupport {

    @Autowired
    private IUserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @NotNull
    private UserDTO userDTO = new UserDTO();

    private String login;

    private String password;

    @NotNull
    private FacesContext facesContext = FacesContext.getCurrentInstance();

    @NotNull
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);

    @NotNull
    private
    String userId = (String) session.getAttribute("userId");

    public String loginUser() {
        @Nullable final UserDTO userDTO = userService.userLogin(login, password);
        if (userDTO != null) {
            session.setAttribute("userId", userDTO.getId());
            return "/WEB-INF/views/project/project-list.xhtml?faces-redirect=true";
        } else {
            return "user-login?faces-redirect=true";
        }
    }

    public String logout() {
        session.invalidate();
        return "user-login?faces-redirect=true";
    }

    public UserDTO findUser() {
        @Nullable final UserDTO userDTO = userService.findUser(userId);
        if (userDTO != null) {
            this.userDTO = userDTO;
        }
        return this.userDTO;
    }

    public String userEdit() {
        @NotNull final String hashPassword = Objects.requireNonNull(bCryptPasswordEncoder.encode(password));
        userDTO.setHashPassword(hashPassword);
        userService.editUser(userDTO.getId(), userDTO);
        return "/WEB-INF/views/user/user-login.xhtml?faces-redirect=true";
    }

    public String registration() {
        System.out.println("!!!!!!!!!!!!" + login);
        if (!login.isEmpty() && !password.isEmpty()) {
            @NotNull final UserDTO newUserDTO = new UserDTO();
            newUserDTO.setId(UUID.randomUUID().toString());
            newUserDTO.setLogin(login);
            @NotNull final String hashPassword = Objects.requireNonNull(bCryptPasswordEncoder.encode(password));
            newUserDTO.setHashPassword(hashPassword);
            newUserDTO.setRole(Role.ADMIN);
            userService.createUser(newUserDTO.getId(), newUserDTO);
            return "user-login?faces-redirect=true";
        } else {
            return "user-registry?faces-redirect=true";
        }
    }
}