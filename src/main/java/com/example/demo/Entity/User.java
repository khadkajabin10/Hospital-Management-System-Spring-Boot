package com.example.demo.Entity;

import com.example.demo.Entity.type.AuthProviderType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table (name = "app_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(unique = true)
    private String username;
    private String password;
    private String providerId;
    @Enumerated(EnumType.STRING)
    private AuthProviderType authProviderType;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//this is for defining what user has what authority like it is admin or doctor or paient etc
        return List.of();
    }
}
