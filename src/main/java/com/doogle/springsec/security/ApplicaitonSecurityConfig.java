package com.doogle.springsec.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicaitonSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicaitonSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .antMatchers("/api/**").hasRole(ApplicationUserRole.STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails studentUser = User.builder()
                .username("std")
                .password(passwordEncoder.encode("123"))
                .roles(ApplicationUserRole.STUDENT.name()).build(); // ROLE_STUDENT

        UserDetails adminUser = User.builder()
                .username("adm")
                .password(passwordEncoder.encode("1234"))
                .roles(ApplicationUserRole.ADMIN.name()).build(); // ROLE_ADMIN

        UserDetails adminTrnUser = User.builder()
                .username("admtr")
                .password(passwordEncoder.encode("12345"))
                .roles(ApplicationUserRole.ADMINTRAINEE.name()).build(); // ROLE_ADMIN

        return new InMemoryUserDetailsManager(
                studentUser, adminUser, adminTrnUser
        );
    }
}
