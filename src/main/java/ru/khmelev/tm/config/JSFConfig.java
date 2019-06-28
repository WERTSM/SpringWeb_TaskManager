package ru.khmelev.tm.config;

import com.ocpsoft.pretty.PrettyFilter;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.khmelev.tm.util.ViewScope;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JSFConfig {

    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        final CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        final Map<String, Object> scopes = new HashMap<String, Object>();
        scopes.put(ViewScope.NAME, new ViewScope());
        configurer.setScopes(scopes);
        return configurer;
    }

    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        final String[] urlPatterns = {"*.xhtml"};
        final ServletRegistrationBean registration = new ServletRegistrationBean(new FacesServlet(), urlPatterns);
        registration.setName("Faces Servlet");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<PrettyFilter> prettyFilterRegistration() {
        final FilterRegistrationBean<PrettyFilter> prettyFilter = new FilterRegistrationBean<>(new PrettyFilter());
        prettyFilter.setDispatcherTypes(
                DispatcherType.FORWARD,
                DispatcherType.REQUEST,
                DispatcherType.ASYNC,
                DispatcherType.ERROR);
        prettyFilter.addUrlPatterns("/*");
        return prettyFilter;
    }
}