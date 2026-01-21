package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100,unique = true)
    private String name;
     @OneToOne
     @JoinColumn(name = "HeadDoctor_id" , nullable = false)
    private Doctor headDoctor;
    @ManyToMany
    @JoinTable(
            name="department_doctor"

    )
    private Set<Doctor> doctor_list=new HashSet<>();
}
