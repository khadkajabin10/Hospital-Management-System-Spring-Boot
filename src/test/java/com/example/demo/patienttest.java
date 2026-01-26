package com.example.demo;

import com.example.demo.Entity.Patienttbl;
import com.example.demo.Entity.type.Bloodgrouptype;
import com.example.demo.Repository.PatientRepository;
import com.example.demo.Service.PatientService;
import com.example.demo.dto.Bloodgroupcount;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@SpringBootTest
public class patienttest {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private PatientService patientService;


    @Test

    public void testpatient() {
        //Patienttbl e1=patientService.getentitybyid(4L);
//        Patienttbl patienttbl=patientRepository.findByName("Diana");
//        System.out.println(patienttbl);
       // List<Patienttbl> patienttbl1 = patientRepository.findByGender("male");
       // List<Patienttbl> patienttbl2 = patientRepository.findByBirthdateBetween(LocalDate.of(1990,5,12),LocalDate.of(1998,1,17));
        //List<Patienttbl> patienttbl3=patientRepository.findByNameContainingOrderByIdDesc("di");
//        Patienttbl patienttbl4=patientRepository.findbyname();
//        System.out.println(patienttbl4);
    //    List<Patienttbl> patienttbl5= patientRepository.findByBirthdateAfter(LocalDate.of(1995,11,30));
//      List<Object[]> patienttbl6=patientRepository.countbloodgroup();
//        for ( Object[] objects: patienttbl6) {
//            System.out.println(objects[0]+" "+objects[1]);
//        }
//        int rowaffected=patientRepository.updaterow("ram",2L);
//        System.out.println(rowaffected);
//        List<Bloodgroupcount> patienttbl6=patientRepository.countbloodgroup();
//        for ( Bloodgroupcount bloodgroupcount: patienttbl6) {
//            System.out.println(bloodgroupcount);
//        }
//       Page<Patienttbl> patienttbls=patientRepository.findallPatient(PageRequest.of(2,2 ,Sort.by("name")));
//        for(Patienttbl patient : patienttbls){
//            System.out.println(patient);
//        }
//        Patienttbl patienttbl= patientService.deletePatient(1L);
//        System.out.println(patienttbl);
//        List<Patienttbl> patienttbls=patientRepository.findAllPatient();
//        for(Patienttbl eachpatient:patienttbls){
//        System.out.println(eachpatient);
//        }
    }
}






