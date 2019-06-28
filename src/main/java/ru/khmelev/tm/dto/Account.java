package ru.khmelev.tm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Account {
    private String login;

    private String password;
}