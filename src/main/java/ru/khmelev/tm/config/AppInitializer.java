package ru.khmelev.tm.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{SpringJPAJavaBasedConfig.class, WebMvcConfig.class};
    }

    @Override
    public Class<?>[] getServletConfigClasses() {
        return new Class[]{};
    }

    @Override
    public String[] getServletMappings() {
        return new String[]{"/"};
    }
}