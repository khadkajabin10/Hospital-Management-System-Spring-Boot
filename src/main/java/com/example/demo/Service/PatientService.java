package com.example.demo.Service;

import com.example.demo.Entity.Patienttbl;
import com.example.demo.Repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepository patientRepository;
    @Transactional
    public Patienttbl getentitybyid(Long id){
        Patienttbl e1= patientRepository.findById(id).orElse(null);
        e1.setName("yoyo");
        return e1;
    }



}
