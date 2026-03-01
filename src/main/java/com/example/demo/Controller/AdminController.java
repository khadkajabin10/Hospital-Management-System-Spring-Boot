package com.example.demo.Controller;

import com.example.demo.Service.DoctorService;
import com.example.demo.Service.PatientService;
import com.example.demo.dto.AppointmentResponseDto;
import com.example.demo.dto.DoctorResponseDto;
import com.example.demo.dto.OnboardDoctorRequestDto;
import com.example.demo.dto.PatientResponsDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final PatientService patientService;
    private final DoctorService doctorService;
    @GetMapping("/patients")
    public ResponseEntity< List<PatientResponsDto>> getAllpatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize

    ){
        return ResponseEntity.ok( patientService.getAllpatient(pageNumber,pageSize));
    }

    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnboardDoctorRequestDto onboardDoctorRequestDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onboardDoctorRequestDto));
    }
}
