package com.example.demo.Entity;

import com.example.demo.Entity.type.Bloodgrouptype;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@ToString
@Getter
@Setter
@Table(
        name = "Patient",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_name_birth", columnNames = {"name", "Birthdate"})
        }
)
public class Patienttbl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(nullable = false,unique = true )
    private String email;

    @Column(name = "birthdate")
    private LocalDate birthdate;
    private String gender;
    @Enumerated(EnumType.STRING)
    private Bloodgrouptype bloodGroup;
}
