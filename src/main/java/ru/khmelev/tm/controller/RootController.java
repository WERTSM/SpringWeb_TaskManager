package ru.khmelev.tm.controller;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.khmelev.tm.util.ViewScope;
import ru.khmelev.tm.api.service.IUserService;

@Getter
@Setter
@Component
@Scope(ViewScope.NAME)
@URLMappings(
        mappings = {
                @URLMapping(id = "index", pattern = "/", viewId = "/index.xhtml")
        })
public class RootController extends SpringBeanAutowiringSupport {

    @Autowired
    private IUserService userService;

    @GetMapping("/")
    public String root() {
        return "index";
    }
}