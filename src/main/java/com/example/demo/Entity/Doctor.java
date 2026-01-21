package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private  String name;
    @Column(length = 50)
    private  String specilization;
    @Column(nullable = false, length = 100,unique = true)
    private  String email;
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
    @ManyToMany(mappedBy = "doctor_list")
    private Set<Department> departmentList=new HashSet<>();
}
