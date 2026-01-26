package com.example.demo.Service;

import com.example.demo.Entity.Doctor;
import com.example.demo.Repository.DoctorRepository;
import com.example.demo.dto.DoctorResponseDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

@RequiredArgsConstructor

public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    public  List<DoctorResponseDto> getalldoctor() {
        List<Doctor> doctors=doctorRepository.findAll();
        return  doctors.stream().map(doctor -> modelMapper.map(doctor,DoctorResponseDto.class))
                .collect(Collectors.toList());
    }
}
