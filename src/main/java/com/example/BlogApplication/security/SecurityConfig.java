package com.example.BlogApplication.security;

import com.example.BlogApplication.entity.AppUser;
import com.example.BlogApplication.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class SecurityConfig {

    @Autowired
    private AppUserRepository appUserRepository;

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Post/**", "/Comment/**").authenticated()
                        .anyRequest().permitAll() // Allow all other requests
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/home", true)
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                AppUser user = appUserRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
                return org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole())
                        .build();
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

