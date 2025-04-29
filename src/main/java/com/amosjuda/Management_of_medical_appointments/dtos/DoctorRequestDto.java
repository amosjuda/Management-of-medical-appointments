package com.amosjuda.Management_of_medical_appointments.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DoctorRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String specialty;

    @Email
    @NotBlank
    private String email;
}
