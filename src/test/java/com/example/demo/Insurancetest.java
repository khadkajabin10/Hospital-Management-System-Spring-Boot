package com.example.demo;

import com.example.demo.Entity.Appointment;
import com.example.demo.Entity.Insurance;
import com.example.demo.Entity.Patienttbl;
import com.example.demo.Service.AppointmentService;
import com.example.demo.Service.InsuranceService;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
public class Insurancetest {
    @Autowired
    private InsuranceService insuranceService;
    @Autowired
    private AppointmentService appointmentService;
//    @Test
//    public void testInsurence(){
//        Insurance insurance=Insurance.builder()
//                .validUntil(LocalDate.of(2030,12,12))
//                .provider("HDFC")
//                .policynumber("HDFC123")
//                .build();
//
//        Patienttbl patienttbl=insuranceService.assignInsurancetoPatient(insurance,1L);
//        System.out.println(patienttbl);
//    }
    @Test
    public void testnewAppointment(){
        Appointment appointment=Appointment.builder()
                .appointmentTime(LocalDateTime.of(2026,11,13,14,25,5))
                .reason("cancer third time")
                .build();

        Appointment appointment1= appointmentService.createAnewAppointment(appointment,4L,1L);

        System.out.println(appointment1);
    }
//    @Test
//    public  void testreAsssign(){
//        Appointment appointment=appointmentService.reAssignAppointmentToOtherDoctor(1L,3L);
//        System.out.println(appointment);
//    }
//    @Test
//    public void removePatientinsurance(){
//      Patienttbl patienttbl= insuranceService.disAssociationofInsurancefromPatient(1L);
//        System.out.println(patienttbl);
//    }
}
