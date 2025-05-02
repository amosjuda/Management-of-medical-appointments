package com.amosjuda.Management_of_medical_appointments.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Doctor name", example = "Mykeias arthur")
    private String name;

    @NotBlank
    @Schema(description = "Doctor specialty", example = "Pediatrician")
    private String specialty;

    @Email
    @NotBlank
    @Schema(description = "Doctor email", example = "mykeiasarthur@gmail.com")
    private String email;
}
