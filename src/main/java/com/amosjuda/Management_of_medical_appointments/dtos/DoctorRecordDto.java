package com.amosjuda.Management_of_medical_appointments.dtos;

import jakarta.validation.constraints.NotBlank;

public record DoctorRecordDto(@NotBlank String name, @NotBlank String specialty, @NotBlank String email) {
}
