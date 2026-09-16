package com.example.demo.Security;

import com.example.demo.Entity.Patienttbl;
import com.example.demo.Entity.User;
import com.example.demo.Entity.type.AuthProviderType;
import com.example.demo.Entity.type.RoleType;
import com.example.demo.Repository.PatientRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthUtil authUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;


    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication=authenticationManager.authenticate(//authenticationManager calls loadUserbyusername method
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(),loginRequestDTO.getPassword())
        );
        User user= (User) authentication.getPrincipal();
        String token= authUtil.generateAccessToken(user);
        return  new LoginResponseDTO(token,user.getId());
    }
    public User singupInternal(SignupRequestDto signupRequestDTO, AuthProviderType authProviderType, String providerid){
        User user=userRepository.findByusername(signupRequestDTO.getUsername()).orElse(null);
        if(user!=null){
            throw new IllegalArgumentException("User already exist");
        }
        user= userRepository.save(User.builder()
                .username(signupRequestDTO.getUsername())
                .providerId(providerid)
                        .authProviderType(authProviderType)

                        .roles(signupRequestDTO.getRoles())
                .build());
        if(authProviderType==AuthProviderType.EMAIL){
            user.setPassword( passwordEncoder.encode(signupRequestDTO.getPassword()));//null xa password dont worry
        }
        user =userRepository.save(user);
        Patienttbl patient=Patienttbl.builder()
                .name(signupRequestDTO.getName())
                .email(signupRequestDTO.getUsername())
                .user(user)

                .build();
        patientRepository.save(patient);

        return user;

    }

    public  SignupResponseDTO signup(SignupRequestDto signupRequestDTO) {
        User user=singupInternal(signupRequestDTO,AuthProviderType.EMAIL,null);

        return modelMapper.map(user, SignupResponseDTO.class);
        //or return new SignupResponseDTO(user.getId(),user.getusernmae())


    }
    @Transactional//bez we save user in database

    public ResponseEntity<LoginResponseDTO> handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {//loginResponseDTO bec we want to send jwt and id;
        //fetch providerid and providertype
        //save providerid and providertype in user
        //if user is new then signup
        //if user already exist then login
        AuthProviderType authProviderType=authUtil.getProviderType(registrationId);
        String providerId=authUtil.determineProviderIdFromOauth2User(oAuth2User,registrationId);
        User user =userRepository
                .findByProviderIdAndAuthProviderType(providerId, authProviderType)
                .orElse(null);
        //If the user already exists in your DB with this provider, you get them back. If not, null.
        String email=oAuth2User.getAttribute("email");
        String name=oAuth2User.getAttribute("name");
        User emailuser=userRepository.findByusername(email).orElse(null);
        //Check if a user already exists in your system with the same email address.
        //
        //Why: Sometimes a user first registered with username/password,
        // then later tries OAuth2 login with Google/GitHub.
        //
        //Result: If a match is found,
        // you can “link” their OAuth2 account to the existing email-based account instead of creating a duplicate.
        if(user==null && emailuser==null){
            //signup  this is new account
            String username=authUtil.determineusernamefromoauth2user(oAuth2User,registrationId,providerId);
            //SignupResponseDTO signupResponseDTO=signup(new LoginRequestDTO(username,null)); //here we need user
           user=singupInternal(new SignupRequestDto(username,null,name,Set.of(RoleType.PATIENT)),authProviderType,providerId);

        }
        else if(user!=null){
            if(email!=null && !email.isBlank() && !email.equals(user.getUsername()) )
                user.setUsername(email);
            userRepository.save(user);
        }
        //Meaning: You found a user by providerId+providerType (so this person has logged in with this provider before).
        //
        //Action: Update their username if the provider now gives you a valid email that differs from what
        // you stored earlier.
        // Then save the updated user.
        else {//user==null && emailUser!=null case
            throw new BadCredentialsException("This email is already registerd with provider ");
        }//user → lookup by providerId + providerType (e.g., Google’s sub, GitHub’s id).
        //emailUser → lookup by email (e.g., jabin@gmail.com).
        //The else branch runs when:
       // user == null → no account exists for this providerId.
//  emailUser != null → but you did find an account with the same email.So the situation is:
//👉 “This person is trying to log in with Google/GitHub/Facebook for the first time,
// but their email is already tied to another account in the system.”
        LoginResponseDTO loginResponseDTO=new LoginResponseDTO(authUtil.generateAccessToken(user),user.getId() );
        return ResponseEntity.ok(loginResponseDTO);
    }
}
