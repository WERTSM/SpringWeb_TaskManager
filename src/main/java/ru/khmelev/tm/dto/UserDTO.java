package ru.khmelev.tm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.khmelev.tm.enumeration.Role;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class UserDTO implements Serializable {

    private String id;

    private String login;

    private String hashPassword;

    private Role role;
}