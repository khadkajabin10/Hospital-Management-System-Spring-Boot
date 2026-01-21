package com.example.demo.Service;

import com.example.demo.Entity.Appointment;
import com.example.demo.Entity.Doctor;
import com.example.demo.Entity.Patienttbl;
import com.example.demo.Repository.AppointmentRepository;
import com.example.demo.Repository.DoctorRepository;
import com.example.demo.Repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
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
       appointment.setDoctor(doctor);// we dont need to save because we are updating and not inserting , since we are doint find by id appointment that means appointment is already exist so we need not to insert any thing  and just update save

       return appointment;

    }

}
