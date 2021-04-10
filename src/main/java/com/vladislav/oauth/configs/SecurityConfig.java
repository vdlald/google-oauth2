package com.vladislav.oauth.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.requiresChannel()
        .anyRequest()
        .requiresSecure()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/vaadinServlet/UIDL/**").permitAll()
        .antMatchers("/vaadinServlet/HEARTBEAT/**").permitAll();
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers(
            "/VAADIN/**",
            "/frontend/**",
            "/webjars/**",
            "/images/**",
            "/frontend-es5/**", "/frontend-es6/**"
        );
  }
}

