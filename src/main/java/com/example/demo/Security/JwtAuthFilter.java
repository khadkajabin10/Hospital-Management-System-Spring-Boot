package com.example.demo.Security;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.security.Security;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private  final AuthUtil authUtil;
    private final UserRepository userRepository;
    private  final HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)//note filterchain
     throws ServletException, IOException {
        try {


            log.info("incoming request:{}", request.getRequestURI());
            final String requestTokenHeader = request.getHeader("Authorization");//requestTokenHeader is just a local variable holding the header value.

            //   Adding final means: “Once this variable is assigned, it cannot be reassigned to another value.”
            //Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6...
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
                filterChain.doFilter(request, response);
            }
            String token = requestTokenHeader.split("Bearer ")[1];
            //"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6..."
            //.split("Bearer ") splits the string into parts:
            //
            //[0] = "" (empty, because it starts with "Bearer ")
            //
            //[1] = "eyJhbGciOiJIUzI1NiIsInR5cCI6..." (the actual JWT)
            //
            //So [1] gives you the token.
            String username = authUtil.getusernameFromToken(token);
            // now we need to put something in securtycontaxholder
            //What do we put inside securtycontaxholder?
            //We put an Authentication object (usually a UsernamePasswordAuthenticationToken or JwtAuthenticationToken) that contains:
            //
            //Principal → the user details (username, roles, etc.)
            //
            //Credentials → usually the password or token (often set to null after authentication for safety)
            //
            //Authorities → the roles/permissions granted to the user
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findByusername(username).orElseThrow();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                //this line do, Creates an Authentication object that represents:
//“This request is being made by this user, with these roles-->user.getAuthorities(), and they are authenticated.”
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);//note setAthentication
            }
            filterChain.doFilter(request, response);
        }catch (Exception ex){
            handlerExceptionResolver.resolveException(request,response,null,ex);
        }
    }//The try–catch is like a bridge:

   // Without it → filter errors die in the filter chain.

   // With it → filter errors are forwarded into Spring MVC’s exception handling,
    // so your controllers/advice can respond consistently.i.e GlobalEcdeptionhandler  there is @RestControllerAdvice
    //we need to send error to @RestControllerAdvice and how so we dont let it die in filter chain
}
