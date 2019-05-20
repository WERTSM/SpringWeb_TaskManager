package ru.khmelev.tm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.khmelev.tm.api.service.IProjectService;
import ru.khmelev.tm.api.service.IUserService;

@Controller
public class ProjecctController {

    @Autowired
    IUserService userService;

    @Autowired
    IProjectService projectService;


}