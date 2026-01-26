package com.example.demo.Controller;

import com.example.demo.Service.AppointmentService;
import com.example.demo.Service.PatientService;
import com.example.demo.dto.AppointmentResponseDto;
import com.example.demo.dto.CreateappointmentRequestDto;
import com.example.demo.dto.PatientResponsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/patients")
@RestController
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    @GetMapping("/profile")
    private ResponseEntity< PatientResponsDto> getpatientbyid(){
        return ResponseEntity.ok( patientService.getbyid(3l));
    }
    @PostMapping("/appointments")
    public ResponseEntity< AppointmentResponseDto> creatnewappointment(@RequestBody CreateappointmentRequestDto createappointmentRequestDto){

     return ResponseEntity.status(HttpStatus.CREATED).body( appointmentService.createnewAppointment(createappointmentRequestDto));
    }
}
