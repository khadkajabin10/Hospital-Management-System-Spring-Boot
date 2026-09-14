package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Doctor {
    @Id
 @Column(name = "user_id")
    private Long id;
    @Column(nullable = false, length = 50)
    private  String name;
    @Column(length = 150)
    private  String specilization;
    @Column(length = 100,unique = true)
    private  String email;
    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;
    @ManyToMany(mappedBy = "doctor_list")
    private Set<Department> departmentList=new HashSet<>();//hashset means initially empty set bez no duplication allowed in list duplication is allowed
}
