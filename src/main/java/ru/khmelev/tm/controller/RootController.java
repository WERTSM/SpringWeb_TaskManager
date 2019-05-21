package ru.khmelev.tm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.khmelev.tm.api.service.IUserService;

@Controller
public class RootController {

    @Autowired
    private IUserService userService;

    @GetMapping("/")
    public String root() {
        return "index";
    }
}