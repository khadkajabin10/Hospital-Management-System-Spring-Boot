package com.example.demo.Controller;

import com.example.demo.Service.PatientService;
import com.example.demo.dto.PatientResponsDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final PatientService patientService;
    @GetMapping("/patients")
    public ResponseEntity< List<PatientResponsDto>> getAllpatients(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            @RequestParam(value = "size", defaultValue = "10") Integer pageSize

    ){
        return ResponseEntity.ok( patientService.getAllpatient(pageNumber,pageSize));
    }
}
