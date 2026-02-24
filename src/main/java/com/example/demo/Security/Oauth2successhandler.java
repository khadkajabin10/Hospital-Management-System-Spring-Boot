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
    private final ObjectMapper objectMapper;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token= (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
        String registrationId=token.getAuthorizedClientRegistrationId();
       ResponseEntity <LoginResponseDTO>loginResponseDTO =authService.handleOAuth2LoginRequest(oAuth2User,registrationId);
       response.setStatus(loginResponseDTO.getStatusCode().value());
       response.setContentType(MediaType.APPLICATION_JSON_VALUE);
       response.getWriter().write(objectMapper.writeValueAsString(loginResponseDTO.getBody()));

    }
}
