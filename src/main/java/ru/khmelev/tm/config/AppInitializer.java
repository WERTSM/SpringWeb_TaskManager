package ru.khmelev.tm.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    public Class<?>[] getRootConfigClasses() {
        return new Class[] { };
    }

    @Override
    public Class<?>[] getServletConfigClasses() {
        return new Class[] { WebMvcConfig.class };
    }

    @Override
    public String[] getServletMappings() {
        return new String[] { "/" };
    }
}