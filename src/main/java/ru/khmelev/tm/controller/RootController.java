package ru.khmelev.tm.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.khmelev.tm.api.service.IUserService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ManagedBean
@RequestScoped
public class RootController extends SpringBeanAutowiringSupport {

    @Autowired
    private IUserService userService;

    @GetMapping("/")
    public String root() {
        return "index";
    }
}