package com.example.demo.Security;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.LoginResponseDTO;
import com.example.demo.dto.SignupResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthUtil authUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication=authenticationManager.authenticate(//authenticationManager calls loadUserbyusername method
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),loginRequestDTO.getPassword())
        );
        User user= (User) authentication.getPrincipal();
        String token= authUtil.generateAccessToken(user);
        return  new LoginResponseDTO(token,user.getId());
    }

    public  SignupResponseDTO signup(LoginRequestDTO signupRequestDTO) {
        User user=userRepository.findByusername(signupRequestDTO.getUsername()).orElse(null);
        if(user!=null){
            throw new IllegalArgumentException("User already exist");
        }
        user = userRepository.save(User.builder()
                        .username(signupRequestDTO.getUsername())
                        .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                .build());
        return modelMapper.map(user, SignupResponseDTO.class);
        //or return new SignupResponseDTO(user.getId(),user.getusernmae())


    }
}
