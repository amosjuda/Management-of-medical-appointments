package com.amosjuda.Management_of_medical_appointments.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PatientRecordDto(@NotBlank String name, @NotBlank String email, @NotNull LocalDate birthdate) {
}
