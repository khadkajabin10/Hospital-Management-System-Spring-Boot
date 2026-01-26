package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateappointmentRequestDto {
    private Long doctorid;
    private Long patientid;
    private LocalDateTime appointmentTime;

    private String reason;
}
