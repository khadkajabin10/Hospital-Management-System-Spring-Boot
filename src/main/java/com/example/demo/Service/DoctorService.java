package com.example.demo.Service;

import com.example.demo.Entity.Doctor;
import com.example.demo.Entity.User;
import com.example.demo.Entity.type.RoleType;
import com.example.demo.Repository.DoctorRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.dto.DoctorResponseDto;
import com.example.demo.dto.OnboardDoctorRequestDto;
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
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public  List<DoctorResponseDto> getalldoctor() {
        List<Doctor> doctors=doctorRepository.findAll();
        return  doctors.stream().map(doctor -> modelMapper.map(doctor,DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    public  DoctorResponseDto onBoardNewDoctor(OnboardDoctorRequestDto onboardDoctorRequestDto) {
        User user=userRepository.findById(onboardDoctorRequestDto.getUserId()).orElseThrow();
//       if(user.getRoles().contains(RoleType.DOCTOR)){
//           throw new IllegalArgumentException("Doctor already exists");
//       } to check doctor with role
        if (doctorRepository.existsById(onboardDoctorRequestDto.getUserId())) {
            throw new IllegalArgumentException("Doctor already exists");
        }
        Doctor doctor=Doctor.builder()
                        .name(onboardDoctorRequestDto.getName())
                                .specilization(onboardDoctorRequestDto.getSpecialization())
                                        .user(user)
                                             .build();
        user.getRoles().add(RoleType.DOCTOR);
        return modelMapper.map(doctorRepository.save(doctor),DoctorResponseDto.class);
    }
}
