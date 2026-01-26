package com.example.demo.Controller;

import com.example.demo.Service.DoctorService;
import com.example.demo.dto.DoctorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/public")
@RestController
@RequiredArgsConstructor
public class HospitalController {
    private final DoctorService doctorService;
    @GetMapping("/doctors")
    public ResponseEntity< List<DoctorResponseDto>> getAlldoctor(){
        return ResponseEntity.ok(doctorService.getalldoctor());
    }
}
