package com.example.demo.Service;

import com.example.demo.Entity.Appointment;
import com.example.demo.Entity.Doctor;
import com.example.demo.Entity.Patienttbl;
import com.example.demo.Repository.AppointmentRepository;
import com.example.demo.Repository.DoctorRepository;
import com.example.demo.Repository.PatientRepository;
import com.example.demo.dto.AppointmentResponseDto;
import com.example.demo.dto.CreateappointmentRequestDto;
import com.example.demo.dto.DoctorResponseDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    @Transactional
    public Appointment createAnewAppointment(Appointment appointment, Long doctorid, Long patientId){
        Doctor doctor=doctorRepository.findById(doctorid).orElseThrow(()->new EntityNotFoundException("id not found"));
        Patienttbl patient=patientRepository.findById(patientId).orElseThrow();
        if(appointment.getId()!=null){
            throw new RuntimeException("something is wrong , while creating new appointment there should not be existing id ");
        }
        appointment.setDoctor(doctor);
        appointment.setPatienttbl(patient);
        return appointmentRepository.save(appointment);// here we save because we try to insert , we are not tring to find appointment by id , so we do save

    }
    @Transactional
    public Appointment reAssignAppointmentToOtherDoctor(Long appointmentid,Long doctorid){
        Doctor doctor=doctorRepository.findById(doctorid).orElseThrow(()->new EntityNotFoundException("id not found"));
        Appointment appointment=appointmentRepository.findById(appointmentid).orElseThrow();
       appointment.setDoctor(doctor);// we dont need to save because we are updating and not inserting , since we are doing find by id ("appointment") that means appointment is already exist so we need not to insert any thing  and just update save

       return appointment;

    }

    public List<AppointmentResponseDto> getAllAppointmentofDoctor(Long  doctorid) {
        Doctor doctor=doctorRepository.findById(doctorid).orElseThrow();
        return doctor.getAppointments()
                .stream()
                .map(appointment ->modelMapper.map(appointment, AppointmentResponseDto.class) )
                .collect(Collectors.toList());
    }

    public  AppointmentResponseDto createnewAppointment(CreateappointmentRequestDto createappointmentRequestDto) {
        Doctor doctor=doctorRepository.findById(createappointmentRequestDto.getDoctorid()).orElseThrow();
        Patienttbl patienttbl=patientRepository.findById(createappointmentRequestDto.getPatientid()).orElseThrow();
        Appointment appointment= Appointment
                .builder()
                .reason(createappointmentRequestDto.getReason())
                .appointmentTime(createappointmentRequestDto.getAppointmentTime())
                .build();
        appointment.setDoctor(doctor);
        appointment.setPatienttbl(patienttbl);
      Appointment newAppointment=  appointmentRepository.save(appointment);
      return modelMapper.map(newAppointment,AppointmentResponseDto.class);

    }
}
