package com.amosjuda.Management_of_medical_appointments.dtos;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientResponseDto {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthdate;
}
