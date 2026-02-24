package com.example.demo.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Configuration
@Slf4j
public class wedsecurityconfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final Oauth2successhandler oauth2successhandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session .sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authorizeHttpRequests(auth ->auth

                        .requestMatchers("/public/**","/auth/**").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/doctors/**").hasAnyRole("ADMIN","DOCTOR")
//                        .requestMatchers("/patients/**").hasAnyRole("PATIENT","ADMIN")
                                .anyRequest().authenticated()//any request  requires a valid JWT.
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oauth->oauth.failureHandler(
                        (request, response, exception) -> {
                            log.error("oAuth2 error {}"+exception.getMessage());
                        }
                )
                        .successHandler(oauth2successhandler)
                );
        //This ensures JWT authentication happens early, so Spring knows the user before checking roles.




//        .formLogin(Customizer.withDefaults());
        return httpSecurity.build();

    }

}
