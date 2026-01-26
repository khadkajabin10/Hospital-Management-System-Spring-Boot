package com.example.demo.dto;


import com.example.demo.Entity.type.Bloodgrouptype;
import lombok.Data;

import java.time.LocalDate;
@Data
public class PatientResponsDto {

    private Long id;
    private String name;
    private LocalDate birthdate;
    private String gender;

    private Bloodgrouptype bloodGroup;


}

