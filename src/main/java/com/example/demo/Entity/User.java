package com.example.demo.Entity;

import com.example.demo.Entity.type.AuthProviderType;
import com.example.demo.Entity.type.RoleType;
import com.example.demo.Security.RolePremissionMapping;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @OneToOne(mappedBy = "user")
    private Doctor doctor;
    @OneToOne(mappedBy = "user")
    private Patienttbl patienttbl;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<RoleType> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {//this is for defining what user has what authority like it is admin or doctor or paient etc
//        return roles.stream()
//                .map(role->new SimpleGrantedAuthority("ROLE_"+role.name()))
//                .collect(Collectors.toSet());
//    }//this method is call in jwtauthfilter to make token thatis  new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); here

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        roles.forEach(
                role -> {
                    Set<SimpleGrantedAuthority> permission = RolePremissionMapping.gettAuthoritesForRole(role);//permission like patient:read,patient:write and collection of them comes here for given role

                    authorities.addAll(permission);//come in collection so addall
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));//along with permission we add role too
                }
        );
        return authorities;
    }
}
