package com.example.demo.dto;

import com.example.demo.Entity.Doctor;
import com.example.demo.Entity.Patienttbl;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
@Data
public class AppointmentResponseDto {

    private Long id;

    private LocalDateTime appointmentTime;

    private String reason;
    private DoctorResponseDto doctor;

}
