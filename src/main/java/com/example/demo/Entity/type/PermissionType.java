package com.example.demo.Entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@Getter
@RequiredArgsConstructor
public enum PermissionType {

    PATIENT_READ("patient:read"),// patient:read these are return when we call PermissionType.PATIENT_READ.getPermission();
    // this is because of getter
    PATIENT_WRITE("patient:write"),//NAME("value")
    APPOINTMENT_READ("appointment:read"),
    APPOINTMENT_WRITE("appointment:write"),
    APPOINTMENT_DELETE("appointment:delete"),
    USER_MANAGE("user:manage"),
    REPORT_VIEW("report:view");
    private final String permission;//it is done because to store value of name i.e all patient:write,appointment:delete,user:manage etc


}
