package com.example.touk.toukparkometer.config.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.inMemoryAuthentication()
                .withUser("user").password("user").roles(
                        SecurityRole.USER.getRole())
                .and()
                .withUser("operator").password("operator").roles(
                        SecurityRole.USER.getRole(), SecurityRole.OPERATOR.getRole())
                .and()
                .withUser("owner").password("owner").roles(
                        SecurityRole.USER.getRole(), SecurityRole.OPERATOR.getRole(), SecurityRole.OWNER.getRole());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //because client may not support csrf (f.e. Postman)
        http
            .authorizeRequests()
                .mvcMatchers("/api/v1/client/**").permitAll()
                .mvcMatchers("/api/v1/operator/**").hasRole(SecurityRole.OPERATOR.getRole())
                .mvcMatchers("/api/v1/owner/**").hasRole(SecurityRole.OWNER.getRole())
            .and()
                .httpBasic();
    }
}