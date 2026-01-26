package com.example.demo.Repository;

import com.example.demo.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
//   @Query(value = "select *from doctor",nativeQuery = true)
//    List<Doctor> getalldoctor();
}