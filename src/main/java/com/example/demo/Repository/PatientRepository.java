package com.example.demo.Repository;

import com.example.demo.Entity.Patienttbl;
import com.example.demo.dto.Bloodgroupcount;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patienttbl,Long> {
//    Patienttbl findByName(String name);
//
//    List<Patienttbl> findByGender(String gender);
//
//    List<Patienttbl> findByBirthdateBetween(LocalDate start, LocalDate end);
//
//    List<Patienttbl> findByNameContainingOrderByIdDesc(String name);

    // @Query("Select p from Patienttbl p where p.bloodGroup=?1")
//   @Query(value = "SELECT * FROM patient WHERE name = 'Hari' ", nativeQuery = true)
//   Patienttbl findbyname();
//   @Query("Select p from Patienttbl p where p.birthdate > :birthdate")
//    List<Patienttbl> findByBirthdateAfter(@Param("birthdate") LocalDate birthdate);
//   @Query("select new com.example.demo.dto.Bloodgroupcount( p.bloodGroup,count(p)) from Patienttbl p group by p.bloodGroup")
//    List<Bloodgroupcount> countbloodgroup();
//    @Transactional //completed successfully or rolled back if something goes wrong.t should be use in update and delete
//    @Modifying// this anotations tells"This @Query changes data; it is not just fetching data." it should be use in update and delete
//    @Query("UPDATE Patienttbl p SET p.name = :name WHERE p.id = :id")
//    int updaterow(@Param("name") String name, @Param("id") Long id);
//    @Query(value = "Select * from patient ",nativeQuery = true)
//    Page<Patienttbl> findallPatient(Pageable pageable);

//    @Query("SELECT p FROM Patienttbl p " +
//            "LEFT JOIN FETCH p.appointments" )
//
//    List<Patienttbl> findAllPatient();
@Query(value = "select * from patient",nativeQuery = true)
    Page<Patienttbl> findAllPatients(Pageable pageable );
}