package ru.khmelev.tm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.khmelev.tm.util.CustomAuthenticationFailHandler;
import ru.khmelev.tm.util.CustomAuthenticationSuccessHandler;
import ru.khmelev.tm.util.ServiceAuthenticationEntryPoint;

@Configuration

@EnableWebSecurity
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityWebMvcConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomAuthenticationFailHandler customAuthenticationFailHandler;

    @Autowired
    private ServiceAuthenticationEntryPoint serviceAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/", "/userLogin", "/userRegistry", "/rest/login/", "/endpoint/", "/endpoint/authenticationEndpoint").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .loginProcessingUrl("/SpringSecurity").successHandler(customAuthenticationSuccessHandler)
                .failureForwardUrl("/login").failureHandler(customAuthenticationFailHandler).permitAll()
                .and()
                .logout().logoutSuccessUrl("/logout").logoutSuccessUrl("/login").permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Configuration
    @Order(1)
    public class SecurityRESTConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/rest/**").authorizeRequests()
                    .antMatchers("/rest/login/").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(serviceAuthenticationEntryPoint)
                    .and()
                    .csrf().disable();
        }
    }

    @Configuration
    @Order(2)
    public class SecuritySOAPConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/endpoint/**").authorizeRequests()
                    .antMatchers(HttpMethod.GET).permitAll()
                    .antMatchers("/endpoint/authenticationEndpoint").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(serviceAuthenticationEntryPoint)
                    .and()
                    .csrf().disable();
        }
    }
}