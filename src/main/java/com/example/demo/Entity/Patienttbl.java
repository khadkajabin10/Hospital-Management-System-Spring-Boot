package com.example.demo.Entity;

import com.example.demo.Entity.type.Bloodgrouptype;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Patienttbl {
    @Id
    @Column(name = "user_id")
    private Long id;

    private String name;
    @Column(nullable = false,unique = true )
    private String email;

    @Column(name = "birthdate")
    private LocalDate birthdate;
    private String gender;
    @Enumerated(EnumType.STRING)
    private Bloodgrouptype bloodGroup;
    @OneToOne
    @MapsId//means the two entities share the same ID value.
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToOne(cascade = {CascadeType.ALL},orphanRemoval = true)
    @JoinColumn(name = "patient_insurance_id")//owening side
    private  Insurance insurance;
    @OneToMany(mappedBy ="patienttbl",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)//patienttbl should be same with appointment class patient instance name,
    // name must be same
    private List<Appointment> appointments=new ArrayList<>();
}
