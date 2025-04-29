package com.amosjuda.Management_of_medical_appointments.dtos;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DoctorResponseDto {
    private UUID id;
    private String name;
    private String specialty;
    private String email;
}
