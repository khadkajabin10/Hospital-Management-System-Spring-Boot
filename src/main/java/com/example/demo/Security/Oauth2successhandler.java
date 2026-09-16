package com.example.demo.Security;

import com.example.demo.dto.LoginResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class Oauth2successhandler implements AuthenticationSuccessHandler {
    private final AuthService authService;
    private final ObjectMapper objectMapper;//Used to convert a Java object into JSON.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token= (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
        //Give me the main identity/user represented by this authentication
        //OAuth2User contains information obtained from the OAuth2 provider so type cast to that format
        String registrationId=token.getAuthorizedClientRegistrationId();
        //see in application.yml the is resistrationid .it is done to check form which google or github person logedin
       ResponseEntity <LoginResponseDTO>loginResponseDTO =authService.handleOAuth2LoginRequest(oAuth2User,registrationId);
       //your own handle code you wrote
       response.setStatus(loginResponseDTO.getStatusCode().value());//Set HTTP status
       response.setContentType(MediaType.APPLICATION_JSON_VALUE);//Tell browser we're sending JSON
       response.getWriter().write(objectMapper.writeValueAsString(loginResponseDTO.getBody()));
        //Convert LoginResponseDTO to JSON
    }
}
