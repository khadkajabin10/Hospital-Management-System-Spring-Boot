package com.example.demo.Service;

import com.example.demo.Entity.Insurance;
import com.example.demo.Entity.Patienttbl;
import com.example.demo.Repository.InsuranceRepository;
import com.example.demo.Repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class InsuranceService {
    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public Patienttbl assignInsurancetoPatient(Insurance insurance,Long patientid){
        Patienttbl patient= patientRepository.findById(patientid).orElseThrow(()->new EntityNotFoundException("Patient not found with this id "+patientid));
        patient.setInsurance(insurance);
        insurance.setPatient(patient);
        //patient is id here
        return patient;
    }
    @Transactional
    public Patienttbl disAssociationofInsurancefromPatient(Long patientid){
        Patienttbl patienttbl=patientRepository.findById(patientid).orElseThrow();
        patienttbl.setInsurance(null);
        return patienttbl;
    }
}
