package com.example.demo.Security;

import com.example.demo.Entity.type.PermissionType;
import com.example.demo.Entity.type.RoleType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.Entity.type.PermissionType.*;
import static com.example.demo.Entity.type.RoleType.*;

public class RolePremissionMapping {
    private static final Map<RoleType, Set<PermissionType>> map =
            Map.of(
                    PATIENT, Set.of
                                    ( PATIENT_READ, APPOINTMENT_READ, APPOINTMENT_WRITE ),
                    DOCTOR, Set.of
                                    ( APPOINTMENT_DELETE, APPOINTMENT_WRITE, APPOINTMENT_READ, PATIENT_READ ),
                    ADMIN, Set.of
                                    ( PATIENT_READ, PATIENT_WRITE, APPOINTMENT_READ, APPOINTMENT_WRITE, APPOINTMENT_DELETE,
                                       USER_MANAGE, REPORT_VIEW ) );

    public static Set<SimpleGrantedAuthority> gettAuthoritesForRole(RoleType role) {
        return map.get(role)//map.get(PATIENT) and returns:{PATIENT_READ,APPOINTMENT_READ, APPOINTMENT_WRITE}
                .stream()//one by one
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                //make simplegrantedauthority  object as SimpleGrantedAuthority('paitent:read") like wise make 3 object and collect to set
                .collect(Collectors.toSet());
    }//look at role and get its all permission and stream them and
}
//Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//authorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
//authorities.add(new SimpleGrantedAuthority("appointment:read"));
//Now the user has two badges:
//
//One for being a doctor.
//
//One for being allowed to read appointments.notice how simplegrantedathority takes role and permission
