package com.amosjuda.Management_of_medical_appointments.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PatientRequestDto {
    @NotBlank
    @Schema(description = "Patient name", example = "Victor Red")
    private String name;

    @Email
    @NotBlank
    @Schema(description = "Patient email", example = "victorred@gmail.com")
    private String email;

    @NotBlank
    @Schema(description = "Patient phone", example = "5588990663434")
    private String phone;

    @NotNull
    @Schema(description = "Patient birthdate", example = "2002-03-24")
    private LocalDate birthdate;
}
