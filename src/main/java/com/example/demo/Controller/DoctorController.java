package com.example.demo.Controller;

import com.example.demo.Service.AppointmentService;
import com.example.demo.Service.DoctorService;
import com.example.demo.dto.AppointmentResponseDto;
import com.example.demo.dto.DoctorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentofDoctor(){
        return ResponseEntity.ok(appointmentService.getAllAppointmentofDoctor(2L));
    }
}
