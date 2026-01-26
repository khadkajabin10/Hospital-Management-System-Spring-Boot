package com.example.demo.Service;

import com.example.demo.Entity.Patienttbl;
import com.example.demo.Repository.PatientRepository;
import com.example.demo.dto.PatientResponsDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final ModelMapper modelMapper;
    private final PatientRepository patientRepository;
    @Transactional
    public Patienttbl getentitybyid(Long id){
        Patienttbl e1= patientRepository.findById(id).orElse(null);
        e1.setName("yoyo");
        return e1;
    }



    public List<PatientResponsDto> getAllpatient(Integer pageNumber, Integer pageSize) {

//        List<Patienttbl> patienttbls=patientRepository.findAll();
//        List<PatientResponsDto> allpatientdto=patienttbls.stream()
//                .map(Patienttbl->modelmapper.map(Patienttbl,PatientResponsDto.class))
//                .toList();
//        return  allpatientdto; or
        return patientRepository.findAllPatients(PageRequest.of(pageNumber, pageSize))
                .stream()
                .map(patient ->modelMapper .map(patient, PatientResponsDto.class))
                .collect(Collectors.toList());

    }

    public  PatientResponsDto getbyid(Long patientid) {
        Patienttbl patient=patientRepository.findById(patientid).orElseThrow();
        return modelMapper.map(patient,PatientResponsDto.class);
    }
}
