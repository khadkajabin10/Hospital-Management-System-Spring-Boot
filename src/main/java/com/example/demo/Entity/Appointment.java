package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime appointmentTime;
    @Column(length = 500)
    private String reason;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "patient-Id",nullable = false)
    private Patienttbl patienttbl;
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "doctor-Id",nullable = false)
    private Doctor doctor;

}
