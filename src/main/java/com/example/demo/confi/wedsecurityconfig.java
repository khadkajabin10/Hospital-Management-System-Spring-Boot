package com.example.demo.confi;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@RequiredArgsConstructor
@Configuration
public class wedsecurityconfig {
    private final PasswordEncoder passwordEncoder;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .authorizeHttpRequests(auth ->auth
                        .requestMatchers("/public/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/doctors/**").hasAnyRole("ADMIN","DOCTOR")
                        .requestMatchers("/patients/**").hasAnyRole("PATIENT","ADMIN")
                )
        .formLogin(Customizer.withDefaults());
        return httpSecurity.build();

    }
    @Bean
    UserDetailsService userDetailsService(){
        UserDetails user1= User.withUsername("admin")
                .password(passwordEncoder.encode("pass"))//this create hash code of pass like 2efgha#*/ like this for security so password is not pass but random hass that is even depveloper dont know
                .roles("ADMIN")
                .build();
        UserDetails user2= User.withUsername("patient")
                .password(passwordEncoder.encode("pass"))
                .roles("PATIENT")
                .build();
        UserDetails user3= User.withUsername("doctor")
                .password(passwordEncoder.encode("pass"))
                .roles("DOCTOR")
                .build();
        return new InMemoryUserDetailsManager(user1,user2,user3);
    }
}
