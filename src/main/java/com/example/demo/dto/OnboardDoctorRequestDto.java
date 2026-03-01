package com.example.demo.dto;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class OnboardDoctorRequestDto {
    private Long userId;
    private String Specialization;
    private String name;
}
