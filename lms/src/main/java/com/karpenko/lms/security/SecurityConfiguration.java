package com.karpenko.lms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {
    @Configuration
    public static class UiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/profile").authenticated()
                    .antMatchers("/**").permitAll()
                    .and()
                    .formLogin()
                    .defaultSuccessUrl("/course")
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access_denied");
        }
    }
    private final UserDetailsService userDetailService;

    @Autowired
    public SecurityConfiguration(@Qualifier("userAuthService") UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Autowired
    public void authConfigure(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("student")
                .password(passwordEncoder.encode("123"))
                .roles("STUDENT")
                .and()
                .withUser("admin")
                .password(passwordEncoder.encode("123"))
                .roles("ADMIN");

        auth.userDetailsService(userDetailService);
    }
}