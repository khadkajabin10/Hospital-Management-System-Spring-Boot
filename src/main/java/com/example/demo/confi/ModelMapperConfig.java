package com.example.demo.confi;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //@Bean
    UserDetailsService userDetailsService(){
        UserDetails user1= User.withUsername("admin")
                .password(passwordEncoder().encode("pass"))//this create hash code of pass like 2efgha#*/ like this for security so password is not pass but random hass that is even depveloper dont know
                .roles("ADMIN")
                .build();
        UserDetails user2= User.withUsername("patient")
                .password(passwordEncoder().encode("pass"))
                .roles("PATIENT")
                .build();
        UserDetails user3= User.withUsername("doctor")
                .password(passwordEncoder().encode("pass"))
                .roles("DOCTOR")
                .build();
        return new InMemoryUserDetailsManager(user1,user2,user3);
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    { return authenticationConfiguration.getAuthenticationManager(); }

}
