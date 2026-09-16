package com.example.demo.Security;

import com.example.demo.Entity.type.RoleType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@RequiredArgsConstructor
@Configuration
@Slf4j
@EnableMethodSecurity
public class wedsecurityconfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final Oauth2successhandler oauth2successhandler;
    private final HandlerExceptionResolver handlerExceptionResolver;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session .sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
                .authorizeHttpRequests(auth ->auth

                        .requestMatchers("/public/**","/auth/**").permitAll()
                        .requestMatchers("/admin/**").hasRole(RoleType.ADMIN.name())
                        .requestMatchers("/doctors/**").hasAnyRole(RoleType.ADMIN.name(),RoleType.DOCTOR.name())
                        .requestMatchers("/patients/**").hasAnyRole(RoleType.ADMIN.name(),RoleType.PATIENT.name())
                                .anyRequest().authenticated()//any request  requires a valid JWT.
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)//add jwtauthfilete before UsernamePasswordAuthenticationFilter.class
                .oauth2Login(oauth->oauth.failureHandler(/*
                .oauth2Login(...)This tells Spring Security:"Enable OAuth2 login for this application."*/
                        (request, response, exception) -> {
                            //this is lambda if fail it give http request response and exceptions
                            log.error("oAuth2 error {}"+exception.getMessage());
                            handlerExceptionResolver.resolveException(request,response,null,exception);
                            //handlerExceptionResolver is used bez The error happens inside the Spring Security/filter layer and it hasnt reach the controller yet
                            // , so we use HandlerExceptionResolver to forward it toward the controller handler i.e global exceptinal handler
                        }
                )
                        .successHandler(oauth2successhandler)//auth.failureHandler().successHandler() thats the flow
                )
                .exceptionHandling(exceptionconfig->exceptionconfig.accessDeniedHandler((request, response, accessDeniedException) -> {
                    handlerExceptionResolver.resolveException(request,response,null,accessDeniedException);
                }))
        ;
        //This ensures JWT authentication happens early, so Spring knows the user before checking roles.




//        .formLogin(Customizer.withDefaults());
        return httpSecurity.build();

    }
//    @Bean
//    UserDetailsService userDetailsService(){
//        UserDetails user1= User.withUsername("admin")
//                .password(passwordEncoder.encode("pass"))//this create hash code of pass like 2efgha#*/ like this for security so password is not pass but random hass that is even depveloper dont know
//                .roles("ADMIN")
//                .build();
//        UserDetails user2= User.withUsername("patient")
//                .password(passwordEncoder.encode("pass"))
//                .roles("PATIENT")
//                .build();
//        UserDetails user3= User.withUsername("doctor")
//                .password(passwordEncoder.encode("pass"))
//                .roles("DOCTOR")
//                .build();
//        return new InMemoryUserDetailsManager(user1,user2,user3);
//    }

}


